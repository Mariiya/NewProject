package com.ksintership.kozhushanmariia.di;

import android.content.Context;

import com.ksintership.kozhushanmariia.contract.AudioPlayerService;
import com.ksintership.kozhushanmariia.repository.TrackPreviewRepository;
import com.ksintership.kozhushanmariia.utils.AudioPlayerServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return this.context;
    }

    @Provides
    @Singleton
    public AudioPlayerService provideAudioPlayerService(TrackPreviewRepository repository) {
        return new AudioPlayerServiceImpl(repository);
    }
}
