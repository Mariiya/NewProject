package com.ksintership.kozhushanmariia.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ksintership.kozhushanmariia.database.AppDatabase;


@Entity(tableName = AppDatabase.TRACK_LIST_TABLE_NAME)
public class EntityTrack {
    // dbId for sorting in database queries
    @PrimaryKey(autoGenerate = true)
    public long dbId;
    public long deezerId;
    public String trackName;
    public String preview;
    public String link;

    public String albumName;
    public String albumCoverMedium;
    public String albumCoverBig;

    public String artistName;

    @Override
    public int hashCode() {
        return (int) deezerId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj instanceof EntityTrack) {
            return ((EntityTrack) obj).deezerId == this.deezerId;
        }
        return false;
    }
}
