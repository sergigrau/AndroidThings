package edu.fje.dam2.androidthings

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import com.google.android.things.contrib.driver.button.ButtonInputDriver
import com.google.android.things.contrib.driver.pwmspeaker.Speaker
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

/** Activitat en Kotlin que reprodueix un so en polsar un dels 3 botons
 * Utilitza PiezoBuzzer
 * @author sergi.grau@fje.edu
 * @version 1.0 15.12.2018
 */
class M04_Piano : Activity() {

    private lateinit var botoA: ButtonInputDriver
    private lateinit var botoB: ButtonInputDriver
    private lateinit var botoC: ButtonInputDriver

    private lateinit var buzzer: Speaker

    private val frequencies = hashMapOf(
            KEYCODE_A to 1000.0,
            KEYCODE_B to 3000.0,
            KEYCODE_C to 5000.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buzzer = RainbowHat.openPiezo()

        botoA = RainbowHat.createButtonAInputDriver(KEYCODE_A)
        botoB = RainbowHat.createButtonBInputDriver(KEYCODE_B)
        botoC = RainbowHat.createButtonCInputDriver(KEYCODE_C)

        botoA.register()
        botoB.register()
        botoC.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        buzzer.close()
        botoA.unregister()
        botoB.unregister()
        botoC.unregister()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val freqAReproduir = frequencies.get(keyCode)
        if (freqAReproduir != null) {
            buzzer.play(freqAReproduir)
            return true
        }
        else {
            return false
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        buzzer.stop()
        return true
    }
}