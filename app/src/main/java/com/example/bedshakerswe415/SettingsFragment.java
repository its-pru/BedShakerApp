package com.example.bedshakerswe415;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Keywords Fragment controls the page on the app which the
 * user can add settings to only activate the switch if received.
 */
public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        // Required empty public constructor
    }

    private void onSettingsButtonClicked() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button myButton = view.findViewById(R.id.submitSettingsButton);
        myButton.setOnClickListener(v -> onSettingsButtonClicked());
        // Inflate the layout for this fragment
        return view;
    }
}