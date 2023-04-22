package com.example.bedshakerswe415;

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
import java.io.IOException;

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
     * Updates the text on the Wifi Screen to notify the user
     * if the connection was successful or not.
     * @param succeeded True if connection was successful, false if it was not.
     */
    private void updateUI(boolean succeeded) {
        TextView descriptionText = getView().findViewById(R.id.wifiDescriptionText);
        if (succeeded) {
            descriptionText.setText("Wifi connected successfully.");
            descriptionText.setTextColor(Color.parseColor("#2dcc7f"));
        } else {
            descriptionText.setText("Something went wrong, try again.");
            descriptionText.setTextColor(Color.parseColor("#de3c3c"));
        }
    }

    /**
     * Returns a spinner object with some custom settings. Settings
     * describe if there is a spinner, the message displayed, and disables
     * the user from closing the spinner.
     * @return the custom progressDialog object
     */
    private ProgressDialog getCustomProgressDialog() {
        MainActivity mainActivity = (MainActivity) getActivity();
        ProgressDialog pd = new ProgressDialog(mainActivity);
        pd.setMessage("Connecting ...");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return pd;
    }

    /**
     * The handler for when the add wifi button is clicked. Will set the text on
     * the page to notify the user if the connection succeeded. Disables button
     * after its clicked, re-enables after setConfig is run.
     * @param myButton The button that is clicked
     * @param sharedPreferences The sharedPreferences being used to access saved data
     */
    private void onConnectWifiButtonClicked(Button myButton, SharedPreferences sharedPreferences) {
        MainActivity mainActivity = (MainActivity) getActivity();

        ProgressDialog progressSpinner = getCustomProgressDialog();
        progressSpinner.show();

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
                    succeeded = ogSwitch.setConfig(wifiName.getText().toString(), wifiPassword.getText().toString());
                    ogSwitch.getstatusCheckandSetSharedPref(sharedPreferences);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                progressSpinner.dismiss();
                mainActivity.runOnUiThread(() -> updateUI(succeeded));
            }
        };

        setUpWifiThread.start();
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
        myButton.setOnClickListener(v -> onConnectWifiButtonClicked(myButton, sharedPreferences));

        // Inflate the layout for this fragment
        return view;
    }
}