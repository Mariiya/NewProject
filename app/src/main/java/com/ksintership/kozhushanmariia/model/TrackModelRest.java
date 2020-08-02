package com.ksintership.kozhushanmariia.model;


import com.google.gson.annotations.SerializedName;

public class TrackModelRest {
    @SerializedName("id")
    Integer id;
    @SerializedName("title")
    String trackName;
    @SerializedName("preview")
    String preview;
    @SerializedName("link")
    String link;

    @SerializedName("album")
    Album album;
    @SerializedName("artist")
    Artist artist;

    class Album {
        @SerializedName("title")
        String title;
        @SerializedName("cover_medium")
        String coverMedium;
        @SerializedName("cover_big")
        String coverBig;
    }

    class Artist {
        @SerializedName("name")
        String name;
    }

}
