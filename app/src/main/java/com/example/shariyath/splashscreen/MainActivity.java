package com.example.shariyath.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

        private String str_logged_in;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.activity_main);


            Thread threadTimer = new Thread() {
                public void run() {
                    try {
                        int logoTimer = 0;
                        while (logoTimer < 2000) {
                            sleep(100);
                            logoTimer = logoTimer + 100;
                        }
                        startActivity(new Intent(MainActivity.this, Home.class));
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    } finally {
                        finish();
                    }
                }
            };
            threadTimer.start();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
        }

        @Override
        protected void onPause() {
            super.onPause();
        }

        @Override
        protected void onRestart() {
            super.onRestart();
        }

        @Override
        protected void onResume() {
            super.onResume();
        }

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onStop() {
            super.onStop();
        }

    }

