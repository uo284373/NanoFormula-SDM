
package com.example.nanoformula.modelo.constructorsStandings;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandingsEscuderias implements Parcelable {

    @SerializedName("MRData")
    @Expose
    public MRData mRData;


    protected StandingsEscuderias(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StandingsEscuderias> CREATOR = new Creator<StandingsEscuderias>() {
        @Override
        public StandingsEscuderias createFromParcel(Parcel in) {
            return new StandingsEscuderias(in);
        }

        @Override
        public StandingsEscuderias[] newArray(int size) {
            return new StandingsEscuderias[size];
        }
    };

    public MRData getMRData() {
        return mRData;
    }

    public void setMRData(MRData mRData) {
        this.mRData = mRData;
    }
}
