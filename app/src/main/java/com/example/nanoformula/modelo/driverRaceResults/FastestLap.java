
package com.example.nanoformula.modelo.driverRaceResults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FastestLap {

    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("lap")
    @Expose
    private String lap;
    @SerializedName("Time")
    @Expose
    private Time__1 time;
    @SerializedName("AverageSpeed")
    @Expose
    private AverageSpeed averageSpeed;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLap() {
        return lap;
    }

    public void setLap(String lap) {
        this.lap = lap;
    }

    public Time__1 getTime() {
        return time;
    }

    public void setTime(Time__1 time) {
        this.time = time;
    }

    public AverageSpeed getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(AverageSpeed averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

}
