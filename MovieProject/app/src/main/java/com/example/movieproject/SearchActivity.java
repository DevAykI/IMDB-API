package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Context context= this;
        Intent i = getIntent();
        String text = i.getStringExtra("name");

        // finding list view
        ListView listview = (ListView) findViewById(R.id.displaylistview);
        SQLDatabase sqlDatabase = new SQLDatabase(this); // Getting SQLDatabase
        List<MovieModel> All = sqlDatabase.getSearchedChildren(((TextView)findViewById(R.id.lookUpText)).getText().toString()); // Getting All the movies
        Collections.sort(All, new Comparator<MovieModel>() { // comparator to sort in alphabetical order!
            @Override
            public int compare(MovieModel lhs, MovieModel rhs) {
                return rhs.getTitle().compareTo(lhs.getTitle());
            }
        });
        MyAdapter adapter = new MyAdapter(this, All,"search"); // Creating an adapter with all the movie information.
        listview.setAdapter(adapter); // Inserting the rows into the listview!

        Button lookupbutton = findViewById(R.id.lookUp); // finding save button
        lookupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                List<MovieModel> All = sqlDatabase.getSearchedChildren(((TextView)findViewById(R.id.lookUpText)).getText().toString());
                MyAdapter newadapter = new MyAdapter(context, All , "search");
                listview.setAdapter(newadapter); // Inserting the rows into the listview!
            }
        });
    }

}