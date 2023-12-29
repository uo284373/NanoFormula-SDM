
package com.example.nanoformula.modelo.allDrivers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverTable {

    @SerializedName("Drivers")
    @Expose
    private List<Driver> drivers;

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

}
