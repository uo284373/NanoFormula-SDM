
package com.example.nanoformula.modelo.allDrivers;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDrivers implements Parcelable {

    @SerializedName("MRData")
    @Expose
    private MRData mRData;

    protected AllDrivers(Parcel in) {
    }

    public static final Creator<AllDrivers> CREATOR = new Creator<AllDrivers>() {
        @Override
        public AllDrivers createFromParcel(Parcel in) {
            return new AllDrivers(in);
        }

        @Override
        public AllDrivers[] newArray(int size) {
            return new AllDrivers[size];
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
