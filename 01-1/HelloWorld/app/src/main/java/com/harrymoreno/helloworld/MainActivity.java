package com.harrymoreno.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Hello World");
        String[] myStrings = {"a", "b", "c"};
        try {
            Log.i(LOG_TAG, myStrings[10]);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }
}
