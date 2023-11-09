package com.example.nanoformula.API;

import com.example.nanoformula.modelo.constructorRaceResults.ConstructorRaceResults;
import com.example.nanoformula.modelo.constructorsStandings.StandingsEscuderias;
import com.example.nanoformula.modelo.driverQualifyingResults.DriverQualifyingResults;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driversStandings.Standings;

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

    @GET("current/constructorStandings.json")
    Call<StandingsEscuderias> getConstructorsClasification();

    @GET("constructors/{constructorName}/constructorStandings/1.json")
    Call<StandingsEscuderias> getConstructorsChampionStandings(@Path("constructorName") String constructorName);

    @GET("constructors/{constructorName}/results/{position}.json")
    Call<ConstructorRaceResults> getConstructorsRaceResults(@Path("constructorName") String constructorName, @Path("position") int position);

    @GET("constructors/{constructorName}/constructorStandings.json")
    Call<StandingsEscuderias> getConstructorStandings(@Path("constructorName") String constructorName);
}
