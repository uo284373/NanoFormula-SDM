
package com.example.nanoformula.modelo.raceSchedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaceTable {

    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("Races")
    @Expose
    private List<Race> races;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

}
