package com.example.movieproject;

public class MovieModel {
    private int id;
    private String title;
    private String year;
    private String director;
    private String actors;
    private int rating;
    private String review;
    private boolean isfavourite;

    // constructors


    public MovieModel(int id, String title, String year, String director, String actors, int rating, String review, boolean isfavourite) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.actors = actors;
        this.rating = rating;
        this.review = review;
        this.isfavourite = isfavourite;
    }

    //to string


    @Override
    public String toString() {
        return "MovieModel{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", rating='" + rating + '\'' +
                ", review='" + review + '\'' +
                ", isfavourite=" + isfavourite +
                '}';
    }

    //getters and settings
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(boolean isfavourite) {
        this.isfavourite = isfavourite;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) { // need it to set the id after the database gives an id
        this.id = id;
    }
}
