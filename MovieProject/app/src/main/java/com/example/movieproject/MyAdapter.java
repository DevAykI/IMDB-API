package com.example.movieproject;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.content.Context;
import java.util.List;

class MyAdapter extends ArrayAdapter<MovieModel> {
    private static final String TAG = "MovieListAdapter";
    ArrayList<String> tickedStrings = new ArrayList<String>();
    ArrayList<String> untickedStrings = new ArrayList<String>();
    private Context context;
    private String p;

    MyAdapter(Context c, List<MovieModel> list, String purpose){
        super(c,R.layout.my_row, list);
        this.context = c;
        this.p = purpose; // creating a purpose so i don't need to create a new adapter for each situation, it would be no good!

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        // getting all movie information from the list from MovieModel.
        int ID  = getItem(position).getid();
        String Title = getItem(position).getTitle();
        String Year = getItem(position).getYear();
        String Director = getItem(position).getDirector();
        String Actor = getItem(position).getActors();
        int Rating = getItem(position).getRating();
        String Review = getItem(position).getReview();
        Boolean Fav = getItem(position).getIsfavourite();

        View row;
        LayoutInflater layoutInflater;
        // creating a layout inflater for each row
        if (p == "edit"){ // checking the purpose of the
            layoutInflater = (LayoutInflater)context.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.my_edit_row,parent,false); // less information is required for edit command so a different layout is chosen
            Button editbutton = row.findViewById(R.id.EditButton);
            editbutton.setText("Edit: "+Title);
            editbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) { // opening a new activity with all the info about the movie that needs to be edited!

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", ID);
                    bundle.putString("title", Title);
                    bundle.putString("year", Year);
                    bundle.putString("director", Director);
                    bundle.putString("actor", Actor);
                    bundle.putInt("rating", Rating);
                    bundle.putString("review", Review);
                    bundle.putBoolean("fav",Fav);
                    Intent i = new Intent(context, EditActivity.class);
                    i.putExtras(bundle);
                    context.startActivity(i);
                    Activity activity = (Activity) context;
                    activity.finish(); // closes the edit activity so the user cannot go back
                }
            });

        }else if (p== "search"){
            layoutInflater = (LayoutInflater)context.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.my_row,parent,false);
            ((CheckBox)row.findViewById(R.id.checkBox)).setClickable(false);// the user should not be able to change favourites

        }else{
            layoutInflater = (LayoutInflater)context.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.my_row,parent,false);
        }


        // Getting all the important view's which will display the information to the user
        TextView movieTitle = row.findViewById(R.id.MovieTitle);
        TextView movieExtra = row.findViewById(R.id.MovieExtra);
        TextView movieRating = row.findViewById(R.id.MovieRating);
        TextView movieReview = row.findViewById(R.id.MovieReview);
        CheckBox checkbox = row.findViewById(R.id.checkBox);

        // Populating the view's with information from the MovieModel
        movieTitle.setText(Title);
        movieExtra.setText(Html.fromHtml("<b>Director(s):</b> " +Director+"<br><b>Actor(s)</b>: " +Actor+"<br><b>Year Made:</b> " +Year));
        movieRating.setText("Rating: "+Rating+"/10");
        movieReview.setText(Review);
        checkbox.setChecked(Fav);

        // checking if the checkbox is already checked and adding to an array to easily manage
        if (checkbox.isChecked()){
            tickedStrings.add(String.valueOf(ID));
        }else{
            untickedStrings.add(String.valueOf(ID));
        }
        //A onChecked function to see when the checkbox is clicked to therefore relay the information using a getter
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    tickedStrings.add(String.valueOf(ID));
                    untickedStrings.remove(String.valueOf(ID));
                }else{

                    tickedStrings.remove(String.valueOf(ID));
                    untickedStrings.add(String.valueOf(ID));
                }

            }
        });

        return row;
    }
    ArrayList<String> getTickedString(){ // this getter is required for task 3, returns all isfavourite!
        return tickedStrings;
    }
    ArrayList<String> getUnTickedString(){ // this getter is required for task 4, returns all unfavourited!
        return untickedStrings;
    }




}
