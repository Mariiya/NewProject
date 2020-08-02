package com.ksintership.kozhushanmariia.di;

import com.ksintership.kozhushanmariia.rest.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RestModule {

    @Provides
    @Singleton
    RestClient provideRestClient() {
        return new RestClient();
    }

}
