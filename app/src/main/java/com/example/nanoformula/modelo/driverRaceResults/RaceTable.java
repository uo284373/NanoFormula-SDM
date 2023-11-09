
package com.example.nanoformula.modelo.driverRaceResults;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaceTable {

    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("Races")
    @Expose
    private List<Race> races;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

}
