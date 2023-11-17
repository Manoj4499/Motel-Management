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













}
