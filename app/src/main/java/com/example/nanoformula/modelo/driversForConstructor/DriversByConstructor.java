
package com.example.nanoformula.modelo.driversForConstructor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriversByConstructor {

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
