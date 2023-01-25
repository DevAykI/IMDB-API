package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void RegisterMovie(View v){
        Intent i = new Intent(this, RegisterMoviesActivity.class);
        i.putExtra("info","");
        startActivity(i);
    }
    public void DisplayMovies(View v){
        Intent i = new Intent(this, DisplayMoviesActivity.class);
        i.putExtra("info","");
        startActivity(i);
    }
    public void FavouritesMovie(View v){
        Intent i = new Intent(this, FavouritesActivity.class);
        i.putExtra("info","");
        startActivity(i);
    }
    public void EditMoviesMovie(View v){
        Intent i = new Intent(this, EditMoviesActivity.class);
        i.putExtra("info","");
        startActivity(i);
    }
    public void SearchMovie(View v){
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra("info","");
        startActivity(i);
    }
    public void RatingMovie(View v){
        Intent i = new Intent(this, RatingActivity.class);
        i.putExtra("info","");
        startActivity(i);
    }

}