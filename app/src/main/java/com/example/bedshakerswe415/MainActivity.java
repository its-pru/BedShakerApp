package com.example.bedshakerswe415;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
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
        switch1.setPrivateIP(ipCheck);

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


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    boolean toggle = false;
    public void toggle(View view) throws IOException {
       TextView label = findViewById(R.id.lblToggle);
       toggle = !toggle;
       if(toggle) {
        Switch switch1 = new Switch(0);
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