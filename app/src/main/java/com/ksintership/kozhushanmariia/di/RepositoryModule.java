package com.ksintership.kozhushanmariia.di;

import android.content.Context;

import com.ksintership.kozhushanmariia.model.NoteRepository;
import com.ksintership.kozhushanmariia.model.NoteRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class RepositoryModule {

    // singleton used while we not implemented DB cache
    @Provides
    @Singleton
    NoteRepository provideNoteRepository(Context context) {
        return new NoteRepositoryImpl(context);
    }
}

