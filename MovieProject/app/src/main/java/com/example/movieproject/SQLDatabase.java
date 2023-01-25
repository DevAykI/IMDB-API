package com.example.movieproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLDatabase extends SQLiteOpenHelper {

    public static final String MOVIE_TABLE = "MOVIE_TABLE";
    public static final String MOVIE_TITLE = "MOVIE_TITLE";
    public static final String MOVIE_YEAR = "MOVIE_YEAR";
    public static final String MOVIE_DIRECTOR = "MOVIE_DIRECTOR";
    public static final String MOVIE_ACTORS = "MOVIE_ACTORS";
    public static final String MOVIE_RATING = "MOVIE_RATING";
    public static final String MOVIE_REVIEW = "MOVIE_REVIEW";
    public static final String MOVIE_FAVOURITES = "MOVIE_FAVOURITES";
    public static final String MOVIE_ID = "ID";

    public SQLDatabase(@Nullable Context context){
        super(context, "Movie.db", null, 1);
    }
    // this is called the first time database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MOVIE_TABLE + " (" + MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MOVIE_TITLE + " TEXT, " + MOVIE_YEAR + " TEXT, " + MOVIE_DIRECTOR + " TEXT, " + MOVIE_ACTORS + " TEXT, " + MOVIE_RATING + " INTEGER, " + MOVIE_REVIEW + " TEXT, " + MOVIE_FAVOURITES + " BOOL)";
        db.execSQL(createTableStatement);
    }
    // this is called if the database version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean updateFav(int id, int booleanValue){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateTableStatement = "UPDATE "+ MOVIE_TABLE +" SET "+MOVIE_FAVOURITES+" = "+booleanValue +" WHERE " + MOVIE_ID + " = " + id;

        Cursor cursor = db.rawQuery(UpdateTableStatement,null);
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }
    public void updateall(int id, String title, String year, String director, String actor, int rating, String review, int favourite){
        SQLiteDatabase db = this.getWritableDatabase();
        // surrounding columns with quotation marks when updating more than one column as this is the standard else it won't work.
        String UpdateTableStatement = "UPDATE "+MOVIE_TABLE+" SET "+ MOVIE_TITLE+"=\""+title+"\","+ MOVIE_YEAR+"=\""+year+"\","+ MOVIE_DIRECTOR+"=\""+director+"\","+ MOVIE_ACTORS+"=\""+actor+"\","+MOVIE_RATING+"=\""+rating+"\","+MOVIE_REVIEW+"=\""+review+"\","+MOVIE_FAVOURITES+"=\""+favourite+"\""+" WHERE "+MOVIE_ID+"=\""+id+"\"";
        db.execSQL(UpdateTableStatement);

        db.close();

    }


    public boolean Remove(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String UpdateTableStatement = "DELETE FROM "+ MOVIE_TABLE +" WHERE " + MOVIE_ID + " = " + id;

        Cursor cursor = db.rawQuery(UpdateTableStatement,null);
        if (cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public boolean Add(MovieModel movieModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MOVIE_TITLE,movieModel.getTitle());
        cv.put(MOVIE_YEAR,movieModel.getYear());
        cv.put(MOVIE_DIRECTOR,movieModel.getDirector());
        cv.put(MOVIE_ACTORS,movieModel.getActors());
        cv.put(MOVIE_RATING,movieModel.getRating());
        cv.put(MOVIE_REVIEW,movieModel.getReview());
        cv.put(MOVIE_FAVOURITES,movieModel.getIsfavourite());

        long insert = db.insert(MOVIE_TABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }
    public List<MovieModel> getSearchedChildren(String index){
        List<MovieModel> returnList = new ArrayList<>();
        // getting data from the database
        String queryString = "SELECT * FROM "+MOVIE_TABLE+" WHERE "+
                MOVIE_TITLE+" LIKE '%"+index+"%'"+
                " OR "+MOVIE_YEAR+" LIKE '%"+index+"%'"+
                " OR "+MOVIE_DIRECTOR+" LIKE '%"+index+"%'"+
                " OR "+MOVIE_ACTORS+" LIKE '%"+index+"%'"+
                " OR "+MOVIE_RATING+" LIKE '%"+index+"%'"+
                " OR "+MOVIE_REVIEW+" LIKE '%"+index+"%'";
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do{
                int MovieID = cursor.getInt(0);
                String MovieTitle = cursor.getString(1);
                String MovieYear = cursor.getString(2);
                String MovieDirector = cursor.getString(3);
                String MovieActors = cursor.getString(4);
                int MovieRating = cursor.getInt(5);
                String MovieReview = cursor.getString(6);
                Boolean MovieFavourite = cursor.getInt(7) == 1? true:false;

                MovieModel newMovie = new MovieModel(MovieID,MovieTitle,MovieYear,MovieDirector,MovieActors,MovieRating,MovieReview,MovieFavourite);
                returnList.add(newMovie);
            }while(cursor.moveToNext());
        }else{
            // failure, empty list was found.
        }
        cursor.close();
        db.close();
        return returnList;
    }
    public List<MovieModel> getChildren(){
        List<MovieModel> returnList = new ArrayList<>();
        // getting data from the database
        String queryString = "SELECT * FROM "+ MOVIE_TABLE;
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do{
                int MovieID = cursor.getInt(0);
                String MovieTitle = cursor.getString(1);
                String MovieYear = cursor.getString(2);
                String MovieDirector = cursor.getString(3);
                String MovieActors = cursor.getString(4);
                int MovieRating = cursor.getInt(5);
                String MovieReview = cursor.getString(6);
                Boolean MovieFavourite = cursor.getInt(7) == 1? true:false;

                MovieModel newMovie = new MovieModel(MovieID,MovieTitle,MovieYear,MovieDirector,MovieActors,MovieRating,MovieReview,MovieFavourite);
                returnList.add(newMovie);
            }while(cursor.moveToNext());
        }else{
            // failure, empty list was found.
        }
        cursor.close();
        db.close();
        return returnList;
    }
    public List<MovieModel> getFavouriteChildren(){
        List<MovieModel> returnList = new ArrayList<>();
        // getting data from the database
        String queryString = "SELECT * FROM "+ MOVIE_TABLE+ " WHERE "+MOVIE_FAVOURITES+" = 1";
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do{
                int MovieID = cursor.getInt(0);
                String MovieTitle = cursor.getString(1);
                String MovieYear = cursor.getString(2);
                String MovieDirector = cursor.getString(3);
                String MovieActors = cursor.getString(4);
                int MovieRating = cursor.getInt(5);
                String MovieReview = cursor.getString(6);
                Boolean MovieFavourite = cursor.getInt(7) == 1? true:false;

                MovieModel newMovie = new MovieModel(MovieID,MovieTitle,MovieYear,MovieDirector,MovieActors,MovieRating,MovieReview,MovieFavourite);
                returnList.add(newMovie);
            }while(cursor.moveToNext());
        }else{
            // failure, empty list was found.
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
