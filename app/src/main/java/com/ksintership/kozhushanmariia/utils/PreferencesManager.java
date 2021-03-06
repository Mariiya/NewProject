package com.ksintership.kozhushanmariia.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ksintership.kozhushanmariia.di.AppInjector;

import javax.inject.Inject;


public class PreferencesManager {
    private final static String PREFS_NAME = "com.ksintership.kozhushanmariia.SETTINGS";

    // PREFS KEYS

    private final static String SAVE_LAST_SEARCH = "save_last_search";
    private final static String SAVE_SEARCH_HISTORY = "save_search_history";

    // AUDIO PLAYER PREFS
    private final static String REPEAT_TRACK = "repeat_track_new_key";
    private final static String SHUFFLE_PLAY = "shuffle_play";

    // DEFAULT VALUES
    private final static boolean DEFAULT_IS_SAVE_LAST_SEARCH = true;
    private final static boolean DEFAULT_IS_SAVE_SEARCH_HISTORY = true;

    private final static int     DEFAULT_IS_REPEAT_TRACK = RepeatTrackPref.REPEAT_QUEUE.toInt();
    private final static boolean DEFAULT_IS_SHUFFLE_PLAY = false;

    public static boolean hasSaveLastSearch() {
        confirmInstance();
        return instance.getBoolean(SAVE_LAST_SEARCH, DEFAULT_IS_SAVE_LAST_SEARCH);
    }

    public static void setSaveLastSearch(boolean value) {
        confirmInstance();
        instance.setBoolean(SAVE_LAST_SEARCH, value);
    }

    public static boolean hasSaveSearchHistory() {
        confirmInstance();
        return instance.getBoolean(SAVE_SEARCH_HISTORY, DEFAULT_IS_SAVE_SEARCH_HISTORY);
    }

    public static void setSaveSearchHistory(boolean value) {
        confirmInstance();
        instance.setBoolean(SAVE_SEARCH_HISTORY, value);
    }

    // AUDIO PLAYER PREFS
    public static RepeatTrackPref getRepeatTrack() {
        confirmInstance();
        return RepeatTrackPref.fromInt(instance.getInt(REPEAT_TRACK, DEFAULT_IS_REPEAT_TRACK));
    }

    public static void setRepeatTrack(RepeatTrackPref value) {
        confirmInstance();
        instance.setInteger(REPEAT_TRACK, value.toInt());
    }

    public static boolean hasShufflePlay() {
        confirmInstance();
        return instance.getBoolean(SHUFFLE_PLAY, DEFAULT_IS_SHUFFLE_PLAY);
    }

    public static void setShufflePlay(boolean value) {
        confirmInstance();
        instance.setBoolean(SHUFFLE_PLAY, value);
    }
    // AUDIO PLAYER PREFS END

    private static PreferencesManager instance;

    @Inject
    Context context;

    private SharedPreferences prefs;

    private static void confirmInstance() {
        if (instance == null) instance = new PreferencesManager();
    }

    private PreferencesManager() {
        AppInjector.getAppComponent().inject(this);
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    private void setBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    private void setInteger(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    private int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

}
