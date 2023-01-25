package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    private Button updatebtn;
    private EditText et_title, et_year, et_director, et_actors, et_review;
    private ImageButton st1,st2,st3,st4,st5,st6,st7,st8,st9,st10;
    private CheckBox Fav,notFav;
    private Context current = this;
    private Integer currentRating;
    private boolean currentFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // getting all the information about the movie
            int id = bundle.getInt("id");
            String title = bundle.getString("title");
            String year = bundle.getString("year");
            String director = bundle.getString("director");
            String actor = bundle.getString("actor");
            int rating = bundle.getInt("rating");
            String review = bundle.getString("review");
            Boolean fav = bundle.getBoolean("fav");

            // getting all the fields that the user can interact with and filling with data that is already known
            updatebtn = this.findViewById(R.id.Update);
            et_title =this.findViewById(R.id.Title);
            et_year = this.findViewById(R.id.Year);
            et_director = this.findViewById(R.id.Director);
            et_actors = this.findViewById(R.id.Actors);
            et_review = this.findViewById(R.id.Review);
            Fav = this.findViewById(R.id.favCheckbox);
            notFav = this.findViewById(R.id.favCheckbox2);

            et_title.setText(title);
            et_year.setText(year);
            et_director.setText(director);
            et_actors.setText(actor);
            et_review.setText(review);

            // checking if the item is favourite or not
            if (fav){
                currentFav = true;
                Fav.setChecked(true);
                notFav.setChecked(false);
            }else{
                currentFav = false;
                Fav.setChecked(false);
                notFav.setChecked(true);
            }

            // getting image buttons for stars
            currentRating = rating;
            st1 = this.findViewById(R.id.s1);
            st2 = this.findViewById(R.id.s2);
            st3 = this.findViewById(R.id.s3);
            st4 = this.findViewById(R.id.s4);
            st5 = this.findViewById(R.id.s5);
            st6 = this.findViewById(R.id.s6);
            st7 = this.findViewById(R.id.s7);
            st8 = this.findViewById(R.id.s8);
            st9 = this.findViewById(R.id.s9);
            st10 = this.findViewById(R.id.s10);
            ImageButton[] star = {st1,st2,st3,st4,st5,st6,st7,st8,st9,st10}; // adding into array to makes counting the stars easier
            for (int i = 0; i < rating; i++) {
                star[i].setBackgroundResource(R.drawable.star);
            }

            // slightly more complicated code after this point


            Fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { // making sure that the 1 checkbox is always selected
                    if (isChecked) {
                        currentFav = true;
                        notFav.setChecked(false);
                    }else{
                        currentFav = false;
                        notFav.setChecked(true);
                    }

                }
            });
            notFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {// making sure that the 1 checkbox is always selected

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        currentFav = false;
                        Fav.setChecked(false);
                    }else{
                        currentFav = true;
                        Fav.setChecked(true);
                    }

                }
            });
            for (Integer i = 0; i < 10; i++) { // 10 stars
                star[i].setTag(i); // allows me to get the exact star number within the onclick event otherwise this would probably require more lines to work
                star[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        currentRating=(((int) v.getTag())+1); // selected number of stars will be filled
                        Toast.makeText(EditActivity.this, "Rating: "+(((int) v.getTag())+1)+" Stars", Toast.LENGTH_SHORT).show(); // the number of the star that was clicked which i can easily return
                        for (int a = 0; a <=(int) v.getTag() ; a++) {
                            star[a].setBackgroundResource(R.drawable.star);
                        }
                        for (int a = ((int) v.getTag())+1; a < 10; a++) { //remainder of the stars are hallow
                            star[a].setBackgroundResource(R.drawable.hallowedstar);
                        }
                    }
                });
            }
            // doing all the checks from register movie to make sure the entered data is accurate!
            updatebtn.setOnClickListener(new View.OnClickListener() { // update button is clicked?
                @Override
                public void onClick(View v){
                    MovieModel moviemodel;
                    if(!et_title.getText().toString().matches("") && !et_year.getText().toString().matches("") && !et_director.getText().toString().matches("") && !et_actors.getText().toString().matches("") && !(currentRating == 0) && !et_review.getText().toString().matches("")) {
                        if (Integer.parseInt(et_year.getText().toString()) > 1895) {
                            if (currentRating > 0 && currentRating <= 10) { // this is not required but being careful
                                try {
                                    int decidedfav = currentFav? 1:0; // converting to int because database boolean is 0 or 1
                                    SQLDatabase sqlDatabase = new SQLDatabase(current);
                                    // update all i created inside sqldatabase
                                    sqlDatabase.updateall(id, et_title.getText().toString(), et_year.getText().toString(), et_director.getText().toString(), et_actors.getText().toString(), currentRating, et_review.getText().toString(), decidedfav);
                                    Toast.makeText(current, "Movie: "+et_title.getText().toString()+" was successfully updated!", Toast.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    Toast.makeText(current, "error creating movie", Toast.LENGTH_SHORT).show();
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
}