package com.example.partymanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.ArrayList;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int mode = getIntent().getIntExtra("mode", 0);
        EditText note_title = findViewById(R.id.event_title);
        final int count_tmp = getIntent().getIntExtra("count", 0);

        //Mode 2 makes a new note, Mode 3 opens an old one
        if (mode == 2){
            note_title.setText("Note" + count_tmp);
        } else if (mode == 3){
            String title = getIntent().getStringExtra("title");
            String note = getIntent().getStringExtra("note");
            note_title.setText(title);
            EditText event_body = findViewById(R.id.event_details);
            event_body.setText(note);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try{
                    Log.d("trying", "onClick: submit text");
                    submitText(mode, getIntent());
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    private void submitText(final int mode, Intent i) throws ParseException {
        if (mode == 2) {
            //TODO: Make an entry in the database

            //Get the two editText objects
            EditText event_title = findViewById(R.id.event_title);
            EditText event_location = findViewById(R.id.event_title);
            EditText event_date = findViewById(R.id.event_title);
            EditText event_details = findViewById((R.id.event_details));
            // Prepare data intent
            Intent data = new Intent();

            // Gather the text from the two editText
            String event_title_string = event_title.getText().toString();
            String event_location_string = event_location.getText().toString();
            String event_date_string = event_date.getText().toString();
            String event_detials_string = event_details.getText().toString();

            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            final Event event = new Event(event_title_string, event_location_string, event_date_string, user.getUid(), new ArrayList<User>(), event_detials_string);

            //Put the strings into the data-intent object
            data.putExtra("event",event_title_string);
            if (!(user == null)){
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                //Search the database for a user matching this account's unique ID
                final DocumentReference docRef = db.collection("users").document(user.getUid());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {

                                //User exists
                                docRef.update("events", FieldValue.arrayUnion(event));

                            } else {
                                // Just do nothing
                            }
                        } else {
                            //Error
                            Log.d("Error", "get() failed with ", task.getException());
                        }
                    }
                });
            }

            // Activity finished ok, return the data
            setResult(RESULT_OK, data); // set result code and bundle data for response


            finish(); // closes the activity, pass data to parent
        } else if (mode == 3){
            //Get the two editText objects
            EditText note_title = findViewById(R.id.event_title);
            EditText note_body = findViewById((R.id.event_details));
            // Prepare data intent
            Intent data = new Intent();

            // Gather the text from the two editText
            String note_title_string = note_title.getText().toString();
            String note_body_string = note_body.getText().toString();

            //Put the strings into the data-intent object
            data.putExtra("title", note_title_string);
            data.putExtra("body", note_body_string);
            data.putExtra("isNew", false);
            data.putExtra("index", i.getExtras().getInt("index"));

            // Activity finished ok, return the data
            setResult(RESULT_OK, data); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent
        }
    }

}
