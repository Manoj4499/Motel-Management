package com.example.motel_management;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class Room implements Parcelable {
    private String id;

    private String motelId;
    private String type;
    private String description;
    private Boolean occupied;
    private String imageUrl;
    private Long price;

    private Boolean checkIn;

    public Room(String id, String motelId, String type, String description, Boolean occupied, String imageUrl, Long price, Boolean checkIn) {
        this.id = id;
        this.motelId = motelId;
        this.type = type;
        this.description = description;
        this.occupied = occupied;
        this.imageUrl = imageUrl;
        this.price = price;
        this.checkIn = checkIn;
    }


    protected Room(Parcel in) {
        id = in.readString();
        motelId = in.readString();
        type = in.readString();
        description = in.readString();
        byte tmpOccupied = in.readByte();
        occupied = tmpOccupied == 0 ? null : tmpOccupied == 1;
        imageUrl = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
        byte tmpCheckIn = in.readByte();
        checkIn = tmpCheckIn == 0 ? null : tmpCheckIn == 1;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMotelId() {
        return motelId;
    }

    public void setMotelId(String id) {
        this.motelId = id;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setLocation(Boolean occupied) {
        this.occupied = occupied;
    }





}
