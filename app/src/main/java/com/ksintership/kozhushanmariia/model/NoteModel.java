package com.ksintership.kozhushanmariia.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class NoteModel implements Parcelable {
    private final long id;
    private String title;
    private String content;

    public NoteModel() {
        this("", "");
    }

    public NoteModel(String title, String content) {
        this(-1, title, content);
    }

    public NoteModel(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected NoteModel(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteModel> CREATOR = new Creator<NoteModel>() {
        @Override
        public NoteModel createFromParcel(Parcel in) {
            return new NoteModel(in);
        }

        @Override
        public NoteModel[] newArray(int size) {
            return new NoteModel[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof NoteModel) {
            return ((NoteModel) obj).getId() == this.getId();
        } else return false;
    }
}
