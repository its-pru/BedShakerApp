package com.example.bedshakerswe415;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    boolean toggle = false;
    public void toggle(View view) throws IOException {
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
}