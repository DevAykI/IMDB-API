package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterMoviesActivity extends AppCompatActivity {
    //reference
    Button savebtn;
    EditText et_title, et_year, et_director, et_actors, et_rating, et_review;
    Context current = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movies);

        savebtn = findViewById(R.id.Update);
        et_title =findViewById(R.id.Title);
        et_year = findViewById(R.id.Year);
        et_director = findViewById(R.id.Director);
        et_actors = findViewById(R.id.Actors);
        et_rating = findViewById(R.id.Rating);
        et_review = findViewById(R.id.Review);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MovieModel moviemodel;
                if(!et_title.getText().toString().matches("") && !et_year.getText().toString().matches("") && !et_director.getText().toString().matches("") && !et_actors.getText().toString().matches("") && !et_rating.getText().toString().matches("") && !et_review.getText().toString().matches("")) {
                    if (Integer.parseInt(et_year.getText().toString()) > 1895) {
                        if (Integer.parseInt(et_rating.getText().toString()) > 0 && Integer.parseInt(et_rating.getText().toString()) <= 10) {
                            try {
                                moviemodel = new MovieModel(-1, et_title.getText().toString(), et_year.getText().toString(), et_director.getText().toString(), et_actors.getText().toString(), Integer.parseInt(et_rating.getText().toString()), et_review.getText().toString(), false);
                                Toast.makeText(current, "Great!", Toast.LENGTH_SHORT).show();
                                SQLDatabase sqlDatabase = new SQLDatabase(current);
                                boolean success = sqlDatabase.Add(moviemodel);
                                if (success) {
                                    Toast.makeText(current, "Successfully stored to database!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(current, "Failed to add to database!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(current, "error updating movie", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(current, "Rating must be between 1 and 10", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(current, "Year must be greater than 1895", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(current, "Please fill the required fields", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}