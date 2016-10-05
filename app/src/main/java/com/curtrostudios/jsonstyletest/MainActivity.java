package com.curtrostudios.jsonstyletest;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    ConditionalButton test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = new ConditionalButton(this, 2);
        test.findViewById(R.id.testButton);
        //0 is the first index in the array of objects

        Log.d("Color", String.valueOf(Color.BLUE));

    }

}
