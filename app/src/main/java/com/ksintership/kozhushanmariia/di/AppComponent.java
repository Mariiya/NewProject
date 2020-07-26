package com.ksintership.kozhushanmariia.di;

import com.ksintership.kozhushanmariia.fragments.NoteListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RepositoryModule.class})
@Singleton
public interface AppComponent {
    void inject(NoteListFragment noteListFragment);
}
