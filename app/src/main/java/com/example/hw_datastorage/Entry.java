package com.example.hw_datastorage;

import android.os.Parcel;
import android.os.Parcelable;

public class Entry implements Parcelable {
    private int id;
    private String text;
    private String date;

    public Entry(int id, String text, String date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    protected Entry(Parcel in) {
        id = in.readInt();
        text = in.readString();
        date = in.readString();
    }

    public static final Creator<Entry> CREATOR = new Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return id +" "+ text + " " + date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeString(date);
    }
}
