
package com.example.nanoformula.modelo.constructorRaceResults;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RaceTable {

    @SerializedName("constructorId")
    @Expose
    private String constructorId;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("Races")
    @Expose
    private List<Race> races;

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
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
