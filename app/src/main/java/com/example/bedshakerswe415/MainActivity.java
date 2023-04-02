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
    public static final String SHARED_PREFS = "shared_Prefs";

    Switch switch1;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        setUpNavigationPages();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        switch1 = new Switch(0, sharedPreferences);

//        try {
//            switch1.setConfig("Fios-V9QV4","bond832sad5073copy");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            switch1.getstatusCheckandSetSharedPref(sharedPreferences);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        // Gets permission to receive SMS messages
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }
    }

    /**
     * Returns the global switch variable created in MainActivity.
     */
    public Switch getSwitch() {
        return switch1;
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

    /**
     * Sets up the MainActivity and Fragments (the pages of the app).
     * The content view to the Home Page of the app and the logic for changing
     * between pages of the app is set up.
     */
    private void setUpNavigationPages() {
        // Binding helps switch between menu pages
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Please note that this was changed
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
    }

    /**
     * This has to do with receiving SMS messages.
     * @param requestCode The request code passed in
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
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
}