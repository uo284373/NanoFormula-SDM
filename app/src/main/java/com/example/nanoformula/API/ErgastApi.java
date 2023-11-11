package com.example.nanoformula.API;

import com.example.nanoformula.modelo.driverQualifyingResults.DriverQualifyingResults;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.example.nanoformula.modelo.raceResults.RaceResults;
import com.example.nanoformula.modelo.raceSchedule.RaceSchedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ErgastApi {

    @GET("current/driverStandings.json")
    Call<Standings> getDriversClasification();

    @GET("drivers/{driverName}/results/{position}.json")
    Call<DriverRaceResults> getDriverRaceResults(@Path("driverName") String driverName,@Path("position") int position);

    @GET("drivers/{driverName}/driverStandings/1.json")
    Call<Standings> getDriverChampionStandings(@Path("driverName") String driverName);

    @GET("drivers/{driverName}/qualifying/1.json")
    Call<DriverQualifyingResults> getDriverQualifyingResults(@Path("driverName") String driverName);

    @GET("drivers/{driverName}/driverStandings.json")
    Call<Standings> getDriverStandings(@Path("driverName") String driverName);

    @GET("drivers/{driverName}/fastest/1/results.json")
    Call<DriverRaceResults> getDriverFastestLap(@Path("driverName") String driverName);

    @GET("current.json")
    Call<RaceSchedule> getRaceSchedule();

    @GET("current/{number}/results.json")
    Call<RaceResults> getRaceResults(@Path("number") String posRace);
}
