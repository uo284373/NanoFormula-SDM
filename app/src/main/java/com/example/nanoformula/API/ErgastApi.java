package com.example.nanoformula.API;

import com.example.nanoformula.modelo.constructorRaceResults.ConstructorRaceResults;
import com.example.nanoformula.modelo.constructorsStandings.StandingsEscuderias;
import com.example.nanoformula.modelo.driverQualifyingResults.DriverQualifyingResults;
import com.example.nanoformula.modelo.driverRaceResults.DriverRaceResults;
import com.example.nanoformula.modelo.driversForConstructor.DriversByConstructor;
import com.example.nanoformula.modelo.driversStandings.Standings;
import com.example.nanoformula.modelo.raceResults.RaceResults;
import com.example.nanoformula.modelo.raceResultsByConstructor.RaceResultsByConstructor;
import com.example.nanoformula.modelo.raceSchedule.RaceSchedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ErgastApi {

    @GET("{temp}/driverStandings.json")
    Call<Standings> getDriversClasification(@Path("temp") String temp);

    @GET("drivers/{driverName}/results/{position}.json")
    Call<DriverRaceResults> getDriverRaceResults(@Path("driverName") String driverName,@Path("position") int position);
    @GET("{temp}/drivers/{driverName}/results.json")
    Call<DriverRaceResults> getDriverRaceResultsForTemp(@Path("temp") String temp,@Path("driverName") String driverName);

    @GET("drivers/{driverName}/driverStandings/1.json")
    Call<Standings> getDriverChampionStandings(@Path("driverName") String driverName);

    @GET("drivers/{driverName}/qualifying/1.json")
    Call<DriverQualifyingResults> getDriverQualifyingResults(@Path("driverName") String driverName);

    @GET("drivers/{driverName}/driverStandings.json")
    Call<Standings> getDriverStandings(@Path("driverName") String driverName);
    @GET("{temp}/drivers/{driverName}/driverStandings.json")
    Call<Standings> getDriverStandingsForTemp(@Path("temp") String temp,@Path("driverName") String driverName);

    @GET("drivers/{driverName}/fastest/1/results.json")
    Call<DriverRaceResults> getDriverFastestLap(@Path("driverName") String driverName);

    @GET("current/constructorStandings.json")
    Call<StandingsEscuderias> getConstructorsClasification();

    @GET("constructors/{constructorName}/constructorStandings/1.json")
    Call<StandingsEscuderias> getConstructorsChampionStandings(@Path("constructorName") String constructorName);

    @GET("constructors/{constructorName}/results/{position}.json")
    Call<ConstructorRaceResults> getConstructorsRaceResults(@Path("constructorName") String constructorName, @Path("position") int position);

    @GET("constructors/{constructorName}/constructorStandings.json?limit=100")
    Call<StandingsEscuderias> getConstructorStandings(@Path("constructorName") String constructorName);

    @GET("{season}/constructors/{constructorName}/drivers.json")
    Call<DriversByConstructor>  getDriversBySeasonAndConstructor(@Path("season") String season, @Path("constructorName") String constructorName);

    @GET("current.json")
    Call<RaceSchedule> getRaceSchedule();

    @GET("current/{number}/results.json")
    Call<RaceResults> getRaceResults(@Path("number") String posRace);

    @GET("{temp}/constructors/{constructorName}/results.json?limit=100")
    Call<RaceResultsByConstructor> getConstructorsRaceResultsForTemp(@Path("temp") String temp, @Path("constructorName") String constructorName);

    @GET("{temp}/constructors/{constructorName}/constructorStandings.json")
    Call<StandingsEscuderias> getConstructorsStandingsForTemp(@Path("temp") String temp,@Path("constructorName") String driverName);
}


