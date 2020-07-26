package com.ksintership.kozhushanmariia.app;

import android.app.Application;

import com.ksintership.kozhushanmariia.di.AppComponent;
import com.ksintership.kozhushanmariia.di.AppModule;
import com.ksintership.kozhushanmariia.di.DaggerAppComponent;

public class NoteApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
