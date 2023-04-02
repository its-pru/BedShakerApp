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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_Prefs";

    Switch switch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        switch1 = new Switch(0, sharedpreferences);
//        try {
//            switch1.setConfig("Fios-V9QV4","bond832sad5073copy");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            switch1.getstatusCheckandSetSharedPref(sharedpreferences);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


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
        toggle = !toggle;
        if (toggle) {
            TextView label = findViewById(R.id.lblToggle);
            toggle = !toggle;
            if (toggle) {
                label.setText("Bed Shaker ON");
                switch1.TurnOn();

            } else {
                label.setText("Bed Shaker OFF");
                switch1.TurnOn();
           }
        }
    }
}