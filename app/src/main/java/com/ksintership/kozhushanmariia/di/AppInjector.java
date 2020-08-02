package com.ksintership.kozhushanmariia.di;

import android.content.Context;

public class AppInjector {
    private static AppInjector instance;

    private Context context;
    private AppComponent appComponent;

    private AppInjector(Context appContext) {
        this.context = appContext;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }

    public static void build(Context appContext) {
        instance = new AppInjector(appContext);
    }

    public static AppComponent getAppComponent() {
        return instance.appComponent;
    }
}
