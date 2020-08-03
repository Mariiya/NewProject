package com.ksintership.kozhushanmariia.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ksintership.kozhushanmariia.model.EntityTrack;

import java.util.List;

@Dao
public interface TrackDao {

    @Query("SELECT * FROM " + AppDatabase.TRACK_LIST_TABLE_NAME + " ORDER BY dbId")
    List<EntityTrack> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EntityTrack model);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<EntityTrack> models);

    @Query("DELETE FROM " + AppDatabase.TRACK_LIST_TABLE_NAME)
    void clear();

}
