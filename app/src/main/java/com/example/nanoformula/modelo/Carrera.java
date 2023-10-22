package com.example.nanoformula.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Carrera implements Parcelable {

    private int position;
    private String name;
    private String location;
    private String country;
    private String date;
    private int flag;

    public Carrera(int position, String name, String location, String country, String date, int flag) {
        this.position = position;
        this.name = name;
        this.location = location;
        this.country = country;
        this.date = date;
        this.flag = flag;
    }


    protected Carrera(Parcel in) {
        position = in.readInt();
        name = in.readString();
        location = in.readString();
        country = in.readString();
        date = in.readString();
        flag = in.readInt();
    }

    public static final Creator<Carrera> CREATOR = new Creator<Carrera>() {
        @Override
        public Carrera createFromParcel(Parcel in) {
            return new Carrera(in);
        }

        @Override
        public Carrera[] newArray(int size) {
            return new Carrera[size];
        }
    };

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(position);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(country);
        parcel.writeString(date);
        parcel.writeInt(flag);
    }
}
