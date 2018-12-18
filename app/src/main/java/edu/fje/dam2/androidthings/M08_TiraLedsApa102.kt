package edu.fje.dam2.androidthings

import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import android.app.Activity
import android.graphics.Color
import com.google.android.things.contrib.driver.apa102.Apa102
import android.os.Bundle
import java.util.*
import kotlin.concurrent.timerTask
/** Activitat en Kotlin que fa us de la tira de leds Apa102
 * Utilitza Apa102
 * @author sergi.grau@fje.edu
 * @version 1.0 20.12.2018
 */
class M08_TiraLedsApa102 : Activity() {
    private val timer: Timer = Timer()
    private var direccioDreta = true
    private var posicioActual = 0
    private val interval: Long = 100

    val leds = IntArray(RainbowHat.LEDSTRIP_LENGTH)
    private lateinit var tiraLeds: Apa102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tiraLeds = RainbowHat.openLedStrip()
        tiraLeds.brightness = Apa102.MAX_BRIGHTNESS
        tiraLeds.direction = Apa102.Direction.NORMAL

        timer.schedule(timerTask {
            moureLeds()
        }, 0, interval)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        tiraLeds.close()
    }

    private fun moureLeds() {
        actualitzarPosicioLed()
        actualitzarTiraLeds()
    }

    private fun actualitzarPosicioLed() {
        if (direccioDreta) {
            posicioActual++
            if (posicioActual == RainbowHat.LEDSTRIP_LENGTH - 1) {
                direccioDreta = false
            }
        } else {
            posicioActual--
            if (posicioActual == 0) {
                direccioDreta = true
            }
        }
    }

    private fun actualitzarTiraLeds() {
        for (i in leds.indices) {
            if (i == posicioActual) {
                leds[i] = Color.RED
            } else {
                leds[i] = Color.TRANSPARENT
            }
        }
        tiraLeds.write(leds)
    }
}