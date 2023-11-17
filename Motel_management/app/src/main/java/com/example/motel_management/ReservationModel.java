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
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(room, i);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(address);
        parcel.writeString(phoneNumber);
        parcel.writeString(idProof);
        parcel.writeLong(startDate != null ? startDate.getTime() : -1);
        parcel.writeLong(endDate != null ? endDate.getTime() : -1);
    }

    // Add getters and setters for startDate and endDate
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }
}
