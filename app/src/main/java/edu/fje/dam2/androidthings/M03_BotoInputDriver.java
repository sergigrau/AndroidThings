package edu.fje.dam2.androidthings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.google.android.things.contrib.driver.button.ButtonInputDriver;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

/** Activitat en Kotlin que encen un led al polsar un botó capacitatiu.
 * Utilitza ButtonDriver en comptes de Button
 * @author sergi.grau@fje.edu
 * @version 1.0 15.12.2018
 */

public class M03_BotoInputDriver extends Activity {

    private Gpio ledVermell, ledVerd;
    private ButtonInputDriver botoA, botoB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            ledVermell = RainbowHat.openLedRed();
            ledVerd = RainbowHat.openLedGreen();
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
                ledVermell.setValue(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else if(keyCode == event.KEYCODE_B){
            try {
                ledVerd.setValue(true);
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
        if (keyCode == event.KEYCODE_A) {
            try {
                ledVermell.setValue(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else if(keyCode == event.KEYCODE_B){
            try {
                ledVerd.setValue(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            botoA.unregister();
            botoB.unregister();
            ledVermell.close();
            ledVerd.close();
        }
        catch (Exception e){

        }

    }
}
