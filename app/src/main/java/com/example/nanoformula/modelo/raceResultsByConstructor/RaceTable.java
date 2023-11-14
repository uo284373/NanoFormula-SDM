
package com.example.nanoformula.modelo.raceResultsByConstructor;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaceTable {

    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("constructorId")
    @Expose
    private String constructorId;
    @SerializedName("Races")
    @Expose
    private List<Race> races;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

}
