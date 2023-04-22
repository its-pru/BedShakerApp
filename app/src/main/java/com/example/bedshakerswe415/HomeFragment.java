package com.example.bedshakerswe415;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.io.IOException;

/**
 * Home Fragment controls the page on the app known as the home
 * page. The page contains some text, and a button which the user
 * can press to turn off the bed shaker when someone sends a message,
 * and to send a message back to the sender.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Describes the actions to take when the "I'm Awake" button on home
     * is clicked. Turns off the switch and sends a message back to the
     * sender's phone which sent you the message.
     */
    private void onHomeButtonClicked() {
        try {
            // Toast.makeText(getActivity(), "Bed Shaker Toggled", Toast.LENGTH_SHORT).show();
            MainActivity.getInstanceActivity().sendSMSandTurnOffSwitch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called when an instance of the home fragment is created. Adds an event
     * listener for when the button is pressed on the home screen.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Creating an event onClickListener to trigger an event when the button is clicked.
        Button myButton = view.findViewById(R.id.homeAwakeButton);
        myButton.setOnClickListener(v -> onHomeButtonClicked());

        // Inflate the layout for this fragment
        return view;
    }

}