package com.example.bedshakerswe415;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Switch switch1 = new Switch(0);
    public static final String SHARED_PREFS = "shared_Prefs";
    public static final String TEXT = "text";
    String ipCheck = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ipCheck = sharedPreferences.getString(TEXT, "");
        //ipCheck = "10.0.0.237";
        switch1.setPrivateIP(ipCheck);
        //System.out.println(ipCheck);

        if (ipCheck.equals("")) {
            String IP;
            try {
                IP = switch1.getStatus();
                System.out.println(IP);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (IP.equals("192.168.33.1")) {
                try {
                    switch1.setConfig();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    IP = switch1.getStatus();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                saveMessage(IP);
            }
        }

//        boolean check;
//        try {
//            check = switch1.getStatus();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (check == true) {
//            try {
//                switch1.setConfig();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
    boolean toggle = false;
    public void toggle(View view) throws IOException {
       TextView label = findViewById(R.id.lblToggle);
       toggle = !toggle;
       if(toggle) {
           label.setText("Bed Shaker ON");
           switch1.TurnOn();

       }
       else {
           label.setText("Bed Shaker OFF");
           switch1.TurnOn();
       }
    }

    private void saveMessage(String IP) {
        SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        // below lines will put values for
        // message in shared preferences.
        editor.putString(TEXT, IP);
        // to save our data with key and value.
        editor.apply();
        // on below line we are displaying a toast message after adding data to shared prefs.
        //Toast.makeText(this, "Message saved to Shared Preferences", Toast.LENGTH_SHORT).show();
        // after that we are setting our edit text to empty
        //messageEdt.setText("");
    }
}