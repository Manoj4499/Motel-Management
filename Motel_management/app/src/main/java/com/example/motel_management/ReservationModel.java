package com.example.motel_management;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class ReservationModel implements Parcelable {

    private Room room;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String idProof;
    private Date startDate; // New field
    private Date endDate;   // New field

    public ReservationModel(Room room, String firstName, String lastName, String address, String phoneNumber, String idProof, Date startDate, Date endDate) {
        this.room = room;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.idProof = idProof;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Add getters and setters for startDate and endDate

    protected ReservationModel(Parcel in) {
        room = in.readParcelable(Room.class.getClassLoader());
        firstName = in.readString();
        lastName = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        idProof = in.readString();
        long startMillis = in.readLong();
        long endMillis = in.readLong();
        startDate = startMillis == -1 ? null : new Date(startMillis);
        endDate = endMillis == -1 ? null : new Date(endMillis);
    }

    public static final Creator<ReservationModel> CREATOR = new Creator<ReservationModel>() {
        @Override
        public ReservationModel createFromParcel(Parcel in) {
            return new ReservationModel(in);
        }

        @Override
        public ReservationModel[] newArray(int size) {
            return new ReservationModel[size];
        }
    };

    // Add getters and setters for startDate and endDate

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
  
}
