package com.example.bedshakerswe415;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    /**
     * MAKE BTN WORK INSIDE FRAGMENT : https://stackoverflow.com/questions/21192386/android-fragment-onclick-button-method
     * https://stackoverflow.com/questions/46308675/why-android-cannot-find-my-onclick-method-inside-fragment
     */
    boolean toggle = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Creating an event onClickListener to trigger an event when the button is clicked.
        Button myButton = view.findViewById(R.id.homeAwakeButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Toggle the switch when the button on home is clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) throws RuntimeException {
                MainActivity mainActivity = (MainActivity) getActivity();

                try {
                    // Toast.makeText(getActivity(), "Bed Shaker Toggled", Toast.LENGTH_SHORT).show();
                    mainActivity.sendSMSandTurnOffSwitch();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // TODO: Add functionality for sending message back to receiver here and turn off the switch.
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}