
package com.example.nanoformula.modelo.driverQualifyingResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverQualifyingResults {

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
