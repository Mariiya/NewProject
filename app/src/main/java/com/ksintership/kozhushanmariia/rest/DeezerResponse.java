package com.ksintership.kozhushanmariia.rest;

import com.google.gson.annotations.SerializedName;
import com.ksintership.kozhushanmariia.model.TrackModelRest;

import java.util.ArrayList;

public class DeezerResponse {
    @SerializedName("data")
    private ArrayList<TrackModelRest> trackList;
    @SerializedName("total")
    private int total;
    @SerializedName("next")
    private String linkToNext;

    public ArrayList<TrackModelRest> getTrackList() {
        return trackList;
    }

    public String getLinkToNext() {
        return linkToNext;
    }

    public int getTotal() {
        return total;
    }
}
