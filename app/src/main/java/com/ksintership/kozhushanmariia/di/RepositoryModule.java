package com.ksintership.kozhushanmariia.di;

import android.content.Context;

import androidx.room.Room;

import com.ksintership.kozhushanmariia.database.AppDatabase;
import com.ksintership.kozhushanmariia.repository.SearchHistoryRepository;
import com.ksintership.kozhushanmariia.repository.SearchHistoryRepositoryImpl;
import com.ksintership.kozhushanmariia.repository.TrackRepository;
import com.ksintership.kozhushanmariia.repository.TrackRepositoryImpl;
import com.ksintership.kozhushanmariia.rest.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RestModule.class, AppModule.class})
public class RepositoryModule {

    @Provides
    @Singleton
    TrackRepository provideTrackRepository(RestClient restClient, AppDatabase db) {
        return new TrackRepositoryImpl(restClient.getApiService(), db.trackDao());
    }

    @Provides
    @Singleton
    SearchHistoryRepository provideSearchHistoryRepository(AppDatabase db) {
        return new SearchHistoryRepositoryImpl(db.searchHistoryDao());
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .build();
    }
}

