package com.ksintership.kozhushanmariia.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ksintership.kozhushanmariia.database.AppDatabase;

@Entity(tableName = AppDatabase.HISTORY_LIST_TABLE_NAME)
public class EntitySearchHistory {
    @PrimaryKey
    public long searchDate;
    public String searchQuery;
}
