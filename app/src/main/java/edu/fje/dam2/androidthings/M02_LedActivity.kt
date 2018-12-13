package edu.fje.dam2.androidthings

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

/** Activitat en Kotlin que permet fer un fil que fa un perpellar un led
 * @author sergi.grau@fje.edu
 * @version 1.0 15.12.2018
 */
class M02_LedActivity : Activity() {

    private lateinit var led: Gpio //no cal inicialitzarla aquí, o fem a oncreate
    private val handler = Handler()

    private val ledRunnable = object: Runnable {
        override fun run() {
            led.value = !led.value
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        led = RainbowHat.openLedGreen()
        handler.post(ledRunnable)
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(ledRunnable)
        led.close()
    }
}