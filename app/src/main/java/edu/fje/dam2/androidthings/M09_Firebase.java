package edu.fje.dam2.androidthings;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.things.contrib.driver.button.ButtonInputDriver;
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;


    /** Activitat en Java que envia dades a Firebase
     * @author sergi.grau@fje.edu
     * @version 1.0 22.12.2018
     */

    public class M09_Firebase extends Activity {

        private int comptador;
        private ButtonInputDriver botoA, botoB;
        private AlphanumericDisplay segment;

        public static final String NOM = "dam2.fje.edu.nom";
        public static final String ID = "dam2.fje.edu.id";

        DatabaseReference DBArtistes;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            DBArtistes = FirebaseDatabase.getInstance().getReference("artistes");


            try{
                segment = RainbowHat.openDisplay();
                segment.clear();
                segment.setEnabled(true);

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
                afegirArtista();
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

                segment.clear();
            }
            catch (Exception e){

            }

        }

        private void afegirArtista() {
            /*String nom = this.nom.getText().toString().trim();
            String genere = this.genere.getSelectedItem().toString();

                String id = DBArtistes.push().getKey();

                M18_Artista artista = new M18_Artista(id, nom, genere);

                DBArtistes.child(id).setValue(artista);

                this.nom.setText("");
*/
        }
    }
