package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class RatingActivity extends AppCompatActivity {
    String[] SelectedValues ={null,null};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Context context= this;
        Intent i = getIntent();
        String text = i.getStringExtra("name");

        // finding list view
        ListView listview = (ListView) findViewById(R.id.displaylistview);
        SQLDatabase sqlDatabase = new SQLDatabase(this); // Getting SQLDatabase
        List<MovieModel> All = sqlDatabase.getChildren(); // Getting All the movies
        Collections.sort(All, new Comparator<MovieModel>() { // comparator to sort in alphabetical order!
            @Override
            public int compare(MovieModel lhs, MovieModel rhs) {
                return rhs.getTitle().compareTo(lhs.getTitle());
            }
        });
        LinearLayout scroll=(LinearLayout) findViewById(R.id.Linearlayout);
        RadioGroup r=new RadioGroup(this);
        r.setOrientation(RadioGroup.VERTICAL);
        RadioGroup.LayoutParams r2;


        for (int counter = 0; counter < All.size(); counter++) {
            String url_string = "https://imdb-api.com/en/API/SearchTitle/k_zbeil8oy/"+All.get(counter).getTitle()+"%20"+All.get(counter).getYear();
            DownloadTask task = new DownloadTask();
            task.execute(url_string);

            RadioButton r1=new RadioButton(this);
            r1.setText(All.get(counter).getTitle());
            r2=new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.MATCH_PARENT);
            r.addView(r1,r2);
            r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Hashtable<String, String> APIreturns = task.getAPI();
                        Log.d("json", APIreturns.get(r1.getText() + "I") + APIreturns.get(r1.getText() + "R")); // decent way to get my get my answers
                        SelectedValues[0] = APIreturns.get(r1.getText() + "R");
                        SelectedValues[1] = APIreturns.get(r1.getText() + "I");

                    }else{

                    }

                }
            });


        }
        Button IMDBbutton = findViewById(R.id.IMDbutton);
        IMDBbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // opening a new activity with all the info about the movie that needs to be edited!

                    if (SelectedValues[0]==null || SelectedValues[0]== "null") {
                        ((TextView) findViewById(R.id.textViewR)).setText("Please reselect the movie and try again?\nAfter multiple attempts please check the spelling or the year of the movie! \n(also make sure the movie actually exists)");
                    }else{
                        ((TextView) findViewById(R.id.textViewR)).setText("The IMDB rating for this movie is: "+ SelectedValues[0]+ "\n\n\n(Due to API being broken i had to calculate the rating myself that is why it may not match exactly to the IMDB rating but i ensure you the calculations are correct!)");
                        ((ImageView)findViewById(R.id.imageViewI)).setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
                        new DownloadImageTask((ImageView) findViewById(R.id.imageViewI)).execute(SelectedValues[1]);

                    }
            }
        });
        scroll.addView(r);
    }
}