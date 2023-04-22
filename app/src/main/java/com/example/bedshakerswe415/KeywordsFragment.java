package com.example.bedshakerswe415;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Keywords Fragment controls the page on the app which the
 * user can add keywords to only activate the switch if received.
 */
public class KeywordsFragment extends Fragment {
    public KeywordsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keywords, container, false);
    }
}