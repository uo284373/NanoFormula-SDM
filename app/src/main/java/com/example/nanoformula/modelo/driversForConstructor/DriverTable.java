
package com.example.nanoformula.modelo.driversForConstructor;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverTable {

    @SerializedName("season")
    @Expose
    private String season;
    @SerializedName("constructorId")
    @Expose
    private String constructorId;
    @SerializedName("Drivers")
    @Expose
    private List<Driver> drivers;

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

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

}
