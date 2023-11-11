
package com.example.nanoformula.modelo.raceSchedule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaceSchedule implements Parcelable {

    @SerializedName("MRData")
    @Expose
    private MRData mRData;

    protected RaceSchedule(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RaceSchedule> CREATOR = new Creator<RaceSchedule>() {
        @Override
        public RaceSchedule createFromParcel(Parcel in) {
            return new RaceSchedule(in);
        }

        @Override
        public RaceSchedule[] newArray(int size) {
            return new RaceSchedule[size];
        }
    };

    public MRData getMRData() {
        return mRData;
    }

    public void setMRData(MRData mRData) {
        this.mRData = mRData;
    }

}
