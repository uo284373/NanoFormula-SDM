
package com.example.nanoformula.modelo.driversStandings;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Standings implements Parcelable {

    @SerializedName("MRData")
    @Expose
    private MRData mRData;

    protected Standings(Parcel in) {
    }

    public static final Creator<Standings> CREATOR = new Creator<Standings>() {
        @Override
        public Standings createFromParcel(Parcel in) {
            return new Standings(in);
        }

        @Override
        public Standings[] newArray(int size) {
            return new Standings[size];
        }
    };

    public MRData getMRData() {
        return mRData;
    }

    public void setMRData(MRData mRData) {
        this.mRData = mRData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }
}
