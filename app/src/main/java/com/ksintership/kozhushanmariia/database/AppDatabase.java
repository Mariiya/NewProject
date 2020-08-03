package com.ksintership.kozhushanmariia.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ksintership.kozhushanmariia.model.EntitySearchHistory;
import com.ksintership.kozhushanmariia.model.EntityTrack;

@Database(entities = {EntityTrack.class, EntitySearchHistory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "main_db";
    public static final String TRACK_LIST_TABLE_NAME = "tracks";
    public static final String HISTORY_LIST_TABLE_NAME = "search_history";

    public abstract TrackDao trackDao();

    public abstract SearchHistoryDao searchHistoryDao();
}
