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
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";
    String ipCheck = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        //ipCheck = sharedpreferences.getString("IP_KEY", "");
        ipCheck = "10.0.0.237";
        switch1.setPrivateIP(ipCheck);
        //System.out.println(ipCheck);

        if (ipCheck.equals("")) {
            String IP;
            try {
                IP = switch1.getStatus();
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
        SharedPreferences.Editor editor = sharedpreferences.edit();
        // below lines will put values for
        // message in shared preferences.
        editor.putString("IP_KEY", IP);
        // to save our data with key and value.
        editor.apply();
        // on below line we are displaying a toast message after adding data to shared prefs.
        Toast.makeText(this, "Message saved to Shared Preferences", Toast.LENGTH_SHORT).show();
        // after that we are setting our edit text to empty
        //messageEdt.setText("");
    }
}