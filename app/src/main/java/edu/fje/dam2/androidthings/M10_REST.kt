package edu.fje.dam2.androidthings

import android.app.Activity
import android.os.Bundle
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import org.json.JSONObject
import fi.iki.elonen.NanoHTTPD

/**
 * Activitat de Android Things.
 * Encasta un servidor HTTPD amb NanoHTTPd per a poder accedir via LAN / WLAN
 * cal posar a dependencies   implementation 'org.nanohttpd:nanohttpd:2.2.0'
 * http://android.local:8080/temperatura
 * enviar com a POST {"status":true} a http://android.local:8080/led amb restlet
 * @author sergi.grau@fje.edu
 * @version 1.0 2.01.2019
 */
class M10_REST : Activity(), ApiListener {

    lateinit var redLed: Gpio
    lateinit var sensorTemperatura: Bmx280

    lateinit var apiServer: ApiServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        redLed = RainbowHat.openLedRed()

        sensorTemperatura = RainbowHat.openSensor()
        sensorTemperatura.setMode(Bmx280.MODE_NORMAL)
        sensorTemperatura.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        redLed.value = true


        apiServer = ApiServer(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        apiServer.stop()
        redLed.close()
        sensorTemperatura.close()
    }

    override fun onGetTemperatura(): JSONObject {
        val resposta = JSONObject()
        val value = sensorTemperatura.readTemperature().toDouble()
        resposta.put("temperatura", value)
        return resposta
    }

    override fun onPostLed(request: JSONObject) {
        val status = request.get("status") as Boolean
        redLed.value = status
    }
}


interface ApiListener {

    fun onGetTemperatura(): JSONObject

    fun onPostLed(request: JSONObject)
}



class ApiServer(private val listener: ApiListener) : NanoHTTPD(8080) {

    init {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession): Response {
        val ruta = session.uri
        val metode = session.method

        if (metode === Method.GET && ruta == "/temperatura") {
            val resposta = listener.onGetTemperatura()
            return newFixedLengthResponse(resposta.toString())
        }
        else if (metode === Method.POST && ruta == "/led") {
            val peticio = obtenirCosComJSON(session)
            listener.onPostLed(peticio)
            return newFixedLengthResponse("")
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT,
                "Error 404, fitxer no trobat.")
    }

    private fun obtenirCosComJSON(sessio: NanoHTTPD.IHTTPSession): JSONObject {
        val fitxers = HashMap<String, String>()
        sessio.parseBody(fitxers)
        var contingut = fitxers["postData"]
        return JSONObject(contingut)
    }
}