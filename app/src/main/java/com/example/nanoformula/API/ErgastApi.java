package com.example.nanoformula.API;

import com.example.nanoformula.modelo.driversStandings.Standings;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ErgastApi {

    @GET("current/driverStandings.json")
    Call<Standings> getDriversClasification();
}
