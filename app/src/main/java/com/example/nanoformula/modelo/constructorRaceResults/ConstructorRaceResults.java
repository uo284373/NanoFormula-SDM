
package com.example.nanoformula.modelo.constructorRaceResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConstructorRaceResults {

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
