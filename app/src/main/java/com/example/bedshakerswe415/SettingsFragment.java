package com.example.bedshakerswe415;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Keywords Fragment controls the page on the app which the
 * user can add settings to only activate the switch if received.
 */
public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * When the submit button is clicked, the values entered into the input is saved
     * to sharedPreferences and the placeholder (hint) values are set to the new values.
     */
    public void onSettingsButtonClicked() {
        saveShakerSettingValues();
        setHintValues();
    }

    /**
     * Saves the shakeTime, timeBetweenShakes, and numOfShakes to shared preferences.
     */
    private void saveShakerSettingValues() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText[] views = getShakerSettingViews();
        int shakeTime = Integer.parseInt(views[0].getText().toString());
        int timeBetweenShakes = Integer.parseInt(views[1].getText().toString());
        int numOfShakes = Integer.parseInt(views[2].getText().toString());
        editor.putInt("shakeTime", shakeTime);
        editor.putInt("timeBetweenShakes", timeBetweenShakes);
        editor.putInt("numOfShakes", numOfShakes);
        editor.apply();
    }

    /**
     * Sets the 3 input fields' hints (in regards to the shake settings) with the
     * values stored in shared preferences for shakeTime, timeBetweenShakes, and numOfShakes.
     */
    private void setHintValues() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);
        String shakeTime = "" + sharedPreferences.getInt("shakeTime", 1);
        String timeBetweenShakes = "" + sharedPreferences.getInt("timeBetweenShakes", 1);
        String numOfShakes = "" + sharedPreferences.getInt("numOfShakes", 5);

        EditText[] views = getShakerSettingViews();

        views[0].setHint(shakeTime);
        views[1].setHint(timeBetweenShakes);
        views[2].setHint(numOfShakes);
    }

    /**
     * Retrieves the 3 input elements' EditText for the shakeTime, timeBetweenShakes, and the numOfShakes.
     * @return An array of EditText elements that relate to the shake settings.
     */
    private EditText[] getShakerSettingViews() {
        EditText shakeTime = (EditText) getView().findViewById(R.id.shakeAmountText);
        EditText timeBetweenShakes = (EditText) getView().findViewById(R.id.intervalAmountText);
        EditText numOfShakes = (EditText) getView().findViewById(R.id.numShakesText);

        Log.d("BEDSHAKER_DEBUG_STATEMENTS_SETTINGS", "ShakeTime Hint: " + shakeTime.getHint().toString());
        return new EditText[] { shakeTime, timeBetweenShakes, numOfShakes };
    }

    /**
     * Called when the view is created.
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button myButton = view.findViewById(R.id.submitSettingsButton);
        myButton.setOnClickListener(v -> onSettingsButtonClicked());
        return view;
    }

    /**
     * In the lifecycle of a faragment, onStart is called after onCreateView.
     * setHintValues() requires a View to be created in order to work.
     */
    @Override
    public void onStart() {
        super.onStart();
        setHintValues();
    }

}