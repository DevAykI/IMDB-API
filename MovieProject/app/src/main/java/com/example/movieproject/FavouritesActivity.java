package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Intent i = getIntent();
        String text = i.getStringExtra("name");
        Context context= this;
        ListView listview = (ListView) findViewById(R.id.displaylistview);
        SQLDatabase sqlDatabase = new SQLDatabase(this); // Getting SQLDatabase
        List<MovieModel>  All = sqlDatabase.getFavouriteChildren(); // Getting All favourite movies
        Collections.sort(All, new Comparator<MovieModel>() { // comparator to sort in alphabetical order!
            @Override
            public int compare(MovieModel lhs, MovieModel rhs) {
                return rhs.getTitle().compareTo(lhs.getTitle());
            }
        });
        MyAdapter adapter = new MyAdapter(this, All,null); // Creating an adapter with all the movie information.
        listview.setAdapter(adapter); // Inserting the rows into the listview!

        Button savebutton = findViewById(R.id.Update); // finding save button
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ArrayList<String> TickedCheckboxes = adapter.getUnTickedString();// getting all the unticked boxes using a getter created inside the adapter

                for (int counter = 0; counter < TickedCheckboxes.size(); counter++) { // creating a for loop to loop through all the unticked boxes to add them to favourites
                    int idselected = Integer.parseInt(TickedCheckboxes.get(counter));
                    sqlDatabase.updateFav(idselected, 0); // The checked boxes will be removed from favourites!
                    Toast.makeText(context, "Changes to selected Movie(s) were successfully applied!", Toast.LENGTH_SHORT).show(); // only removing favourites functionality was required for this part.
                    finish();
                }


                // Do something here.
            }
        });

    }
}