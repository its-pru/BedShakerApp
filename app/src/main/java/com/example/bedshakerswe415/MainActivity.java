package com.example.bedshakerswe415;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    boolean toggle = false;
    public void toggle(View view) {
        TextView label = findViewById(R.id.lblToggle);
       toggle = !toggle;
       if(toggle) {
           label.setText("Bed Shaker ON");
       }
       else {
           label.setText("Bed Shaker OFF");
       }
    }
}