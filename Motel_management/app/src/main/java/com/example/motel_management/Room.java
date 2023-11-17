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











}
