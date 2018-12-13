package edu.fje.dam2.androidthings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

/**
 * Activitat en Java que permet fer un fil fer un cicle entre els leds
 *
 * @author sergi.grau@fje.edu
 * @version 1.0 15.12.2018
 */

public class M02_LedCicleActivity extends Activity {
    private Gpio[] led =new Gpio[3];
    private Handler handler = new Handler();
    private Runnable ledRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                led[0].setValue(! led[0].getValue());
                led[1].setValue(! led[1].getValue());
                led[2].setValue(! led[2].getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            led[0] = RainbowHat.openLedRed();
            led[1] = RainbowHat.openLedGreen();
            led[2] = RainbowHat.openLedBlue();
            handler.post(ledRunnable);

        }
        catch (Exception e){

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(ledRunnable);
        try {
            led[0].close();
            led[1].close();
            led[2].close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
