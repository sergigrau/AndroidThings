package edu.fje.dam2.androidthings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;
import com.google.android.things.contrib.driver.button.Button;

import java.io.IOException;

/** Activitat en Kotlin que encen un led al polsar un botó capacitatiu
 * @author sergi.grau@fje.edu
 * @version 1.0 15.12.2018
 */

public class M03_BotoSimple extends Activity {

    private Gpio ledVermell;
    private Button botoA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            ledVermell = RainbowHat.openLedRed();
            botoA = RainbowHat.openButtonA();
            botoA.setOnButtonEventListener((button, estat) -> {
                try {
                    ledVermell.setValue(estat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
        catch (Exception e){

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            botoA.close();
            ledVermell.close();
        }
        catch (Exception e){

        }

    }
}
