
package com.example.nanoformula.modelo.raceResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaceResults {

    @SerializedName("MRData")
    @Expose
    private MRData mRData;

    public MRData getMRData() {
        return mRData;
    }

    public void setMRData(MRData mRData) {
        this.mRData = mRData;
    }

}
