package edu.fje.dam2.androidthings;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;

import com.google.android.things.contrib.driver.button.ButtonInputDriver;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;


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
public class M06_AlarmaActivity extends Activity {

    private int comptador;
    private Gpio ledVermell;
    private ButtonInputDriver botoA, botoB;
    private AlphanumericDisplay segment;
    private  Speaker altaveu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            ledVermell = RainbowHat.openLedRed();
            segment = RainbowHat.openDisplay();
            segment.clear();
            segment.setEnabled(true);
            altaveu = RainbowHat.openPiezo();

            botoA = RainbowHat.createButtonAInputDriver(KeyEvent.KEYCODE_A);
            botoB = RainbowHat.createButtonBInputDriver(KeyEvent.KEYCODE_B);
            botoA.register();
            botoB.register();

        }
        catch (Exception e){

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_A) {

            try {
                segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
                segment.display(++comptador);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
         if (keyCode == event.KEYCODE_B) {
            for (int i = comptador; i >= 0; i--) {
                try {
                    altaveu.play(10.0);
                    segment.setBrightness(5);
                    segment.display(i);
                    SystemClock.sleep(100);
                    altaveu.stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

        @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            botoA.unregister();
            botoB.unregister();
            ledVermell.close();
            altaveu.close();
            segment.clear();
        }
        catch (Exception e){

        }

    }
}
