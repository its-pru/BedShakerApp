package com.example.bedshakerswe415;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import com.example.bedshakerswe415.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Switch switch1 = new Switch(0);
    public static final String SHARED_PREFS = "shared_Prefs";
    public static final String TEXT = "text";
    String ipCheck = "";

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        // Binding helps switch between menu pages
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Please note that this is changed
        // setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        // Show home fragment when app just opened.
        replaceFragment(new HomeFragment());
        // Handles the logic for switching to a certain page, based on the
        // item the user clicks on in the bottom navigation menu.
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.wifi:
                    replaceFragment(new WifiFragment());
                    break;
                case R.id.keywords:
                    replaceFragment(new KeywordsFragment());
                    break;
                case R.id.contacts:
                    replaceFragment(new ContactsFragment());
                    break;
            }
            return true;
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        ipCheck = sharedPreferences.getString(TEXT, "");
        switch1.setPrivateIP(ipCheck);

//        if (ipCheck.equals("")) {
//            String IP;
//            try {
//                IP = switch1.getStatus();
//                System.out.println(IP);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            if (IP.equals("192.168.33.1")) {
//                try {
//                    switch1.setConfig();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    Thread.sleep(15000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    IP = switch1.getStatus();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                saveMessage(IP);
//            }
//        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }
    }

    /**
     * Replaces the mainFrameLayout item on the main_activity with the fragment
     * passed to the function. A fragment acts like a page in the app, each
     * has a different .xml file.
     * @param fragment The fragment to change to.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
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

//    boolean toggle = false;
//    public void toggle(View view) throws IOException {
//        toggle = !toggle;
//        if (toggle) {
//            TextView awakeButton = findViewById(R.id.homeAwakeButton);
//            toggle = !toggle;
//            if (toggle) {
//                awakeButton.setText("I'm Awake");
//                //switch1.TurnOn();
//            } else {
//                awakeButton.setText("Shake It");
//                //switch1.TurnOn();
//           }
//        }
//    }

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