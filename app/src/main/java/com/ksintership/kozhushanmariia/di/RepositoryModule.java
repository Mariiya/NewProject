package com.ksintership.kozhushanmariia.di;

import com.ksintership.kozhushanmariia.repository.TrackRepository;
import com.ksintership.kozhushanmariia.repository.TrackRepositoryImpl;
import com.ksintership.kozhushanmariia.rest.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = RestModule.class)
public class RepositoryModule {

    @Provides
    @Singleton
    TrackRepository provideTrackRepository(RestClient restClient) {
        return new TrackRepositoryImpl(restClient.getApiService());
    }

}

