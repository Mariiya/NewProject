package com.ksintership.kozhushanmariia.di;

import com.ksintership.kozhushanmariia.fragments.SearchListFragment;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RepositoryModule.class})
@Singleton
public interface AppComponent {
    //Fragments
    void inject(SearchListFragment searchListFragment);

    //ViewModels


    void inject(PreferencesManager preferencesManager);
}
