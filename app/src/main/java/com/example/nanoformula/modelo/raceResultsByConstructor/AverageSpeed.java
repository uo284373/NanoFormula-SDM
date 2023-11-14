
package com.example.nanoformula.modelo.raceResultsByConstructor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AverageSpeed {

    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("speed")
    @Expose
    private String speed;

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

}
