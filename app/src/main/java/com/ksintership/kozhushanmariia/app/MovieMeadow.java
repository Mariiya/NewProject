package com.ksintership.kozhushanmariia.app;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.ksintership.kozhushanmariia.R;

import javax.xml.datatype.Duration;

public class MovieMeadow extends Application {

    private final static String LOG_TAG = MovieMeadow.class.getSimpleName();

    String name;
    Genre genre;
    float rating;
    int year;
    Duration duration;

    @Override
    public void onCreate() {
        super.onCreate();

    }

}
