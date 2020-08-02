package com.ksintership.kozhushanmariia.app;

import android.app.Application;

import com.ksintership.kozhushanmariia.di.AppInjector;

public class NoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.build(this.getApplicationContext());
    }

}
