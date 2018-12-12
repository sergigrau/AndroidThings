package edu.fje.dam2.androidthings;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;


/**
 * Activitat de Android Things.
 * Es tracta d'una alarma compte enrera.
 * <p>
 * Els periferics de Android Things peripheral s'accedeixen amb la classe PeripheralManagerService.
 * <p>
 * @author sergi.grau@fje.edu
 * @version 1.0 30.11.2018
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class AlarmaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try (
                AlphanumericDisplay segment = RainbowHat.openDisplay();
                Speaker altaveu = RainbowHat.openPiezo();
                Gpio led = RainbowHat.openLedRed();
        ) {

            segment.clear();
            segment.setEnabled(true);

            for (int i = 10; i >= 0; i--) {
                altaveu.play(10.0);
                segment.setBrightness(i);
                segment.display(i);
                SystemClock.sleep(100);
            }

            altaveu.stop();

            Button botoA = RainbowHat.openButtonA();

            botoA.setOnButtonEventListener((b, pressionat) -> {
                if (pressionat) {
                    Log.w("DAM2", "polsat");
            }
        });
            botoA.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
