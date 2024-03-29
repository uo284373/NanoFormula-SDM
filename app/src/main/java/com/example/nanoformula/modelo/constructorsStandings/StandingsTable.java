
package com.example.nanoformula.modelo.constructorsStandings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StandingsTable {

    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("StandingsLists")
    @Expose
    private List<StandingsList> standingsLists;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<StandingsList> getStandingsLists() {
        return standingsLists;
    }

    public void setStandingsLists(List<StandingsList> standingsLists) {
        this.standingsLists = standingsLists;
    }

}
