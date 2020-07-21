package com.ksintership.kozhushanmariia.app;

import android.app.Application;

public class MovieMeadow extends Application {

    private final static String LOG_TAG = MovieMeadow.class.getSimpleName();

    String name;
    Genre genre;
    double rating;
    int year;
    String description;

    public MovieMeadow(String name, Genre genre, double rating, int year, String description) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.description = description;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
