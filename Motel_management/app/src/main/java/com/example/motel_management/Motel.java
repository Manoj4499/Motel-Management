package com.example.motel_management;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Motel implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String location;
    private String imageUrl;

    public Motel(String id, String name, String description, String location, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    protected Motel(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        location = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Motel> CREATOR = new Creator<Motel>() {
        @Override
        public Motel createFromParcel(Parcel in) {
            return new Motel(in);
        }

        @Override
        public Motel[] newArray(int size) {
            return new Motel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
