package edu.fje.dam2.androidthings

import android.hardware.SensorManager
import android.app.Activity
import android.hardware.Sensor
import android.os.Bundle
import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

import android.util.Log


/** Activitat en Kotlin que fa us del SensorDriver que s'integra en el SO en comptes
 * de utilitzar Peripherical
 * Utilitza SensorDriver
 * @author sergi.grau@fje.edu
 * @version 1.0 15.12.2018
 */
class M07_SensorDriver : Activity(){

    val sensorCallback = object : SensorManager.DynamicSensorCallback() {
        override fun onDynamicSensorConnected(sensor: Sensor?) {
            if (sensor?.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                registrarTemperaturaListener(sensor)
            }
        }
    }

    val temperaturaSensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            Log.i(TAG, "Temperatura canviada: " + event.values[0])
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            Log.i(TAG, "precissió canviada: $accuracy")
        }
    }

    lateinit var sensorManager : SensorManager
    lateinit var sensorDriver : Bmx280SensorDriver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerDynamicSensorCallback(sensorCallback)

        sensorDriver = RainbowHat.createSensorDriver()
        sensorDriver.registerTemperatureSensor();
    }

    private fun registrarTemperaturaListener(sensor: Sensor) {
        sensorManager.registerListener(temperaturaSensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterDynamicSensorCallback(sensorCallback)
        sensorManager.unregisterListener(temperaturaSensorListener)
        sensorDriver.unregisterTemperatureSensor()
        sensorDriver.close()
    }
}