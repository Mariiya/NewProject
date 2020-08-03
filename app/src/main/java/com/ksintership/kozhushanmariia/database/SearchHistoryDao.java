package com.ksintership.kozhushanmariia.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ksintership.kozhushanmariia.model.EntitySearchHistory;

import java.util.List;

@Dao
public interface SearchHistoryDao {

    @Query("SELECT * FROM " + AppDatabase.HISTORY_LIST_TABLE_NAME + " ORDER BY searchDate DESC")
    List<EntitySearchHistory> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntitySearchHistory entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<EntitySearchHistory> entity);

    @Delete
    void deleteSearchQuery(EntitySearchHistory item);

    @Query("DELETE FROM " + AppDatabase.HISTORY_LIST_TABLE_NAME)
    void clear();
}
