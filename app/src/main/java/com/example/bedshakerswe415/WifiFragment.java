package com.example.bedshakerswe415;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Wifi Fragment controls the page on the app where a user can
 * connect the switch to their own wifi. An event listener is
 * created to respond to when the button is clicked. When the
 * button is clicked a progress loading bar will appear, and the
 * process to connect the switch to the new wifi occurs.
 */
public class WifiFragment extends Fragment {
    public WifiFragment() {
        // Required empty public constructor
    }

    /**
     * Called when an instance of the wifi fragment is created. Adds an event
     * listener for when the button is pressed on the wifi screen.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_wifi, container, false);

        // Creating an event onClickListener to trigger an event when the button is clicked.
        Button myButton = view.findViewById(R.id.wifiConnectButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            /**
             * The handler for when the add wifi button is clicked. Will set the text on
             * the page to notify the user if the connection succeeded. Disables button
             * after its clicked, re-enables after setConfig is run.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                System.out.println("__BUTTON CLICKED__");
                // Retrieving main activity to update ui and switch etc
                MainActivity mainActivity = (MainActivity) getActivity();

                // Display loading progress bar
                ProgressDialog p = new ProgressDialog(mainActivity);
                p.setMessage("Connecting ...");
                p.setCancelable(false);
                p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                p.show();

                myButton.setEnabled(false); // Disable button while waiting

                Switch ogSwitch = mainActivity.getSwitch();
                EditText wifiName = getView().findViewById(R.id.wifiInputNameText);
                EditText wifiPassword = getView().findViewById(R.id.wifiInputPasswordText);

                // Created thread, so the progress dialog would appear while the wifi is connecting.
                // Thread connects to wifi and alters the wifi description message, if connection successful.
                Thread setUpWifiThread = new Thread() {
                    boolean succeeded = false;
                    @Override
                    public void run() {
                        //  call setConfig() from switch and pass parameters from input fields
                        try {
                            System.out.println("__ATTEMPTING__");
                            succeeded = ogSwitch.setConfig(wifiName.getText().toString(), wifiPassword.getText().toString());
                            System.out.println("Success (In Thread): " + succeeded);
                        } catch (IOException e) {
                            System.out.println("__EXCEPTION THROWN__");
                            throw new RuntimeException(e);
                        }
                        try {
                            ogSwitch.getstatusCheckandSetSharedPref(sharedPreferences);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // Remove progress spinner from screen
                        p.dismiss();
                        mainActivity.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                TextView descriptionText = getView().findViewById(R.id.wifiDescriptionText);
                                if (succeeded) {
                                    descriptionText.setText("Wifi connected successfully.");
                                    descriptionText.setTextColor(Color.parseColor("#2dcc7f"));
                                    // TODO: could send user back to home screen...
                                } else {
                                    descriptionText.setText("Something went wrong, try again.");
                                    descriptionText.setTextColor(Color.parseColor("#de3c3c"));
                                }
                            }
                        });
                    }
                };

                setUpWifiThread.start();

                // Update the user message based on the result of getStatus
                myButton.setEnabled(true);

                System.out.println("__ENDED__");
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}