package com.example.partymanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {


    public EventsFragment() {
        // Required empty public constructor
    }

    private final int REQUEST_CODE = 20;
    private int count = 0;
    private ArrayList<String> titles = new ArrayList<String>();
    private ArrayList<String> notes = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.events_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Code is from my Midterm Project
        //Create the listView object
        ListView events_list = (ListView) getView().findViewById(R.id.note_list);
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), titles, notes);
        events_list.setAdapter(customAdapter);

        events_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                //TODO: Make it load all the information and let it be editable if it's yours
                Intent i = new Intent(getActivity(), EventDetailsActivity.class);
                i.putExtra("mode", 3); // pass arbitrary data to launched activity
                i.putExtra("title", titles.get(pos));
                i.putExtra("note", notes.get(pos));
                i.putExtra("index", pos);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        //create_note button is the button at the bottom right of the screen
        FloatingActionButton create_event_button = getView().findViewById(R.id.create_note);

        //Adds an Click Listener to the create_note_button
        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateEventActivity.class);
                i.putExtra("mode", 2); // pass arbitrary data to launched activity
                i.putExtra("count", count); //pass in the number of notes
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.getBooleanExtra("isNew", false)) {
                //TODO: Put it in the firestore database

                // Extract name value from result extras
                Event  event = (Event) data.getExtras().getSerializable("event");
                String title = data.getExtras().getString("title");
                String note = data.getExtras().getString("body");
                titles.add(event.getTitle());
                notes.add(event.getDetails());

                //Create the listView object
                ListView note_list = (ListView) getActivity().findViewById(R.id.note_list);
                CustomAdapter customAdapter = new CustomAdapter(getContext(), titles, notes);
                note_list.setAdapter(customAdapter);

                count++;

                // Let the user know their note has been created
                Toast.makeText(getContext(), title + " created.", Toast.LENGTH_SHORT).show();
            } else {
                //TODO: Update the database

                // Extract name value from result extras
                String title = data.getExtras().getString("title");
                String note = data.getExtras().getString("body");
                int i = data.getExtras().getInt("index");

                //Edit the values in titles and notes at that index
                titles.set(i, title);
                notes.set(i, note);

                //Create the listView object
                ListView note_list = (ListView) getView().findViewById(R.id.note_list);
                CustomAdapter customAdapter = new CustomAdapter(getContext(), titles, notes);
                note_list.setAdapter(customAdapter);

                // Let the user know their note has been edited
                Toast.makeText(getActivity(), title + " has been updated.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
