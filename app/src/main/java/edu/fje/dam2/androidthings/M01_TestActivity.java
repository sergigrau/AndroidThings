package edu.fje.dam2.androidthings;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.contrib.driver.apa102.Apa102;
import com.google.android.things.contrib.driver.bmx280.Bmx280;
import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;


/**
 * Activitat de Android Things. Permet testar tots els components de rainbow hat
 * <p>
 * Els periferics de Android Things peripheral s'accedeixen amb la classe
 * PeripheralManagerService.
 * <p>
 * @author sergi.grau@fje.edu
 * @version 1.0 30.11.2018
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class M01_TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        try {

           //prova el led vermell
            Gpio led = RainbowHat.openLedRed();
            led.setValue(true);
            led.close();
            //prova del segment de leds
            AlphanumericDisplay segment = RainbowHat.openDisplay();
            segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
            segment.display("FJE");
            segment.setEnabled(true);
            // tanquem el dispositiu quan està fet
            segment.close();
            //prova del sensor de temperatura
            Bmx280 sensor = RainbowHat.openSensor();
            sensor.setTemperatureOversampling(Bmx280.OVERSAMPLING_1X);
            Log.d("FJE", "temperatura:" + sensor.readTemperature());
            // tanquem el dispositiu quan està fet
            sensor.close();
            //prova de la tira de leds
            Apa102 tiraLEDs = RainbowHat.openLedStrip();
            tiraLEDs.setBrightness(1);
            int[] arcSantMarti = new int[RainbowHat.LEDSTRIP_LENGTH];
            for (int i = 0; i < arcSantMarti.length; i++) {
                arcSantMarti[i] = Color.HSVToColor(255, new float[]{i * 360.f / arcSantMarti.length, 1.0f, 1.0f});
            }
            tiraLEDs.write(arcSantMarti);
            // tanquem el dispositiu quan està fet
            tiraLEDs.close();

            // detectem si s'ha polsat el botó A
            Button boto = RainbowHat.openButtonB();
            boto.setOnButtonEventListener(new Button.OnButtonEventListener() {
                @Override
                public void onButtonEvent(Button button, boolean polsat) {
                    Log.d("FJE", "B polsat:" + polsat);
                }
            });
            // tanquem el dispositiu quan està fet
            boto.close();

            //

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
