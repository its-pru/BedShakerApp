package com.example.bedshakerswe415;

import android.app.Activity;
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

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WifiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WifiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiFragment newInstance(String param1, String param2) {
        WifiFragment fragment = new WifiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
                boolean succeeded = false;
                myButton.setEnabled(false); // Disable button while waiting

                //  1. getActivity() to get MainActivity and switch
                MainActivity mainActivity = (MainActivity) getActivity();
                Switch ogSwitch = mainActivity.getSwitch();
                EditText wifiName = getView().findViewById(R.id.wifiInputNameText);
                EditText wifiPassword = getView().findViewById(R.id.wifiInputPasswordText);

                //  2. call setConfig() from switch and pass parameters from input fields
                try {
                    System.out.println("__ATTEMPTING__");
                    succeeded = ogSwitch.setConfig(wifiName.getText().toString(), wifiPassword.getText().toString());
                } catch (IOException e) {
                    System.out.println("__EXCEPTION THROWN__");
                    throw new RuntimeException(e);
                }
                try {
                    ogSwitch.getstatusCheckandSetSharedPref(sharedPreferences);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // 3. Update the user message based on the result of getStatus
                myButton.setEnabled(true);
                TextView descriptionText = getView().findViewById(R.id.wifiDescriptionText);
                if (succeeded) {
                    descriptionText.setText("Wifi connected successfully.");
                    descriptionText.setTextColor(Color.parseColor("#2dcc7f"));
                    // TODO: could send user back to home screen...
                } else {
                    descriptionText.setText("Something went wrong, try again.");
                    descriptionText.setTextColor(Color.parseColor("#de3c3c"));
                }
                System.out.println("__ENDED__");
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}