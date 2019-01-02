package edu.fje.dam2.androidthings

import android.app.Activity
import java.util.Timer
import android.os.Bundle
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import kotlin.concurrent.timerTask

/** Activitat amb Kotlin que utilitza la classe Timer en comptes de un fil creat de manera manual
 *
 */
class M02_LedTimer : Activity() {

    private lateinit var led: Gpio
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        led = RainbowHat.openLedGreen()
        timer.schedule ( timerTask {
            led.value = !led.value
        }, 0, 1000 )
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        timer.purge()
        led.close()
    }
}