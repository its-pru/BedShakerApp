package com.example.bedshakerswe415;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Contacts Fragments controls the page on the app which the
 * user can add contacts to receive messages from.
 */
public class ContactsFragment extends Fragment {

    private ArrayList<String> contacts;
    private ArrayAdapter<String> contactsAdapter;
    private ListView contactListView;
    private Button button;
    private SharedPreferences sharedPreferences;
    private final static String contactsSP = "contactsList";

    public ContactsFragment() {
        // Required empty public constructor
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
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("shared_Prefs", Context.MODE_PRIVATE);

        contactListView = view.findViewById(R.id.contactsList);
        button = view.findViewById(R.id.contactsAddButton);

        button.setOnClickListener(v -> addContact());

        createContactsList();
        setUpListViewListener();

        return view;
    }

    /**
     * Adds contact to list on fragment and to list in shared preferences.
     */
    private void addContact() {
        EditText inputName = getView().findViewById(R.id.contactsInputNameText);
        EditText inputPhone = getView().findViewById(R.id.contactsInputPhoneText);
        String nameStr = inputName.getText().toString();
        String phoneStr = inputPhone.getText().toString(); // TODO - format string to just be list of numbers i.e. 1234567890

        // Add name and phone to shared preferences
        ArrayList<String> temp = new ArrayList<>(contacts);
        temp.add(nameStr + "\n(" + phoneStr + ")");
        putListInSharedPreferences(temp);

        if (!(nameStr.equals("") && phoneStr.equals(""))) {
            contactsAdapter.add(nameStr + "\n(" + phoneStr + ")"); // Also adds value to contact ArrayList
            inputName.setText("");
            inputPhone.setText("");
        } else {
            Toast.makeText(getContext(), "Please enter a valid string.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Retrieves contacts from shared preferences. If no list in shared preferences, then
     * an empty list is set for contacts.
     */
    private void createContactsList() {
        contacts = new ArrayList<>();

        //Retrieve contacts from shared preferences
        Set set = sharedPreferences.getStringSet(contactsSP, null);
        contacts = (set != null) ? new ArrayList<>(set) : new ArrayList<>();

        contactsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, contacts);
        contactListView.setAdapter(contactsAdapter);
    }

    /**
     * Handles the event when a user clicks and holds on an item in the list it will be deleted.
     */
    private void setUpListViewListener() {
        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeContact(position);
                return true;
            }
        });
    }

    /**
     * Removes item from list and shared preferences.
     * @param itemPos
     */
    private void removeContact(int itemPos) {
        contacts.remove(itemPos); // remove from array list

        //Remove from shared preferences
        putListInSharedPreferences(contacts);

        contactsAdapter.notifyDataSetChanged();
    }

    /**
     * Takes an ArrayList, and places it into shared preferences as a set.
     * @param list The ArrayList to be placed in shared preferences.
     */
    private void putListInSharedPreferences(ArrayList<String> list) {
        Set<String> set = new HashSet<>(list);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(contactsSP, set);
        editor.apply();
    }



}