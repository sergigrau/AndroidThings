package edu.fje.dam2.androidthings

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
/** Activitat en Kotlin que fa una lectura directe dels sensors BMX280
 * Llegeix la temperatura i/o la pressió atmosfèrica i ho mostra en el display
 * @author sergi.grau@fje.edu
 * @version 1.0 15.12.2018
 */
class M05_LecturaDirecteBMX280: Activity() {

    private val handler = Handler()

    private lateinit var sensor: Bmx280
    private lateinit var alphanumericDisplay: AlphanumericDisplay

    val filMostrarTemperatura = object: Runnable {
        override fun run() {
            val temperatura = sensor.readTemperature().toDouble()
            alphanumericDisplay.display(temperatura)
            handler.post(this)
        }
    }
    val filMostrarPressio = object: Runnable {
        override fun run() {
            val pressure = sensor.readPressure().toDouble()
            alphanumericDisplay.display(pressure)
            handler.post(this)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensor = RainbowHat.openSensor()
        sensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        sensor.setMode(Bmx280.MODE_NORMAL)
        sensor.pressureOversampling = Bmx280.OVERSAMPLING_1X

        alphanumericDisplay = RainbowHat.openDisplay()
        alphanumericDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        alphanumericDisplay.setEnabled(true)

        handler.post(filMostrarPressio)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(filMostrarPressio)
        sensor.close()
        alphanumericDisplay.close()
    }
}