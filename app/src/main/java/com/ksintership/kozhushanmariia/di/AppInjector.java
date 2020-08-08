package com.ksintership.kozhushanmariia.di;

import android.content.Context;

import com.ksintership.kozhushanmariia.presenter.PresenterStoreImpl;

public class AppInjector {
    private static AppInjector instance;

    private Context context;
    private AppComponent appComponent;
    private PresenterProvider presenterProvider;

    private AppInjector(Context appContext) {
        this.context = appContext;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
        presenterProvider = new PresenterProviderImpl(new PresenterStoreImpl());
    }

    public static void build(Context appContext) {
        instance = new AppInjector(appContext);
    }

    public static AppComponent getAppComponent() {
        return instance.appComponent;
    }

    public static PresenterProvider getPresenterProvider() {
        return instance.presenterProvider;
    }
}
