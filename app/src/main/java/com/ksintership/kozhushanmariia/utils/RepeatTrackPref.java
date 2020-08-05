package com.ksintership.kozhushanmariia.utils;

public enum RepeatTrackPref {
    NOT_REPEAT, REPEAT_QUEUE, REPEAT_ONE;

    public int toInt() {
        return this.ordinal();
    }

    public static RepeatTrackPref fromInt(int pref) {
        return values()[pref];
    }

    public RepeatTrackPref nextPref() {
        switch (this) {
            case NOT_REPEAT:
                return REPEAT_QUEUE;
            case REPEAT_QUEUE:
                return REPEAT_ONE;
            case REPEAT_ONE:
                return NOT_REPEAT;
        }
        return NOT_REPEAT;
    }
}
