package com.example.nanoformula.API;

import com.example.nanoformula.modelo.driversImage.DriverImage;
import com.example.nanoformula.modelo.raceImage.RaceImage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaApi {
//http://en.wikipedia.org/w/api.php?action=query&titles=Lando_Norris&prop=pageimages&format=json&formatversion=2
    @GET("w/api.php?action=query&prop=pageimages&format=json&formatversion=2&pithumbsize=500")
    Call<DriverImage> getImageDriver(
        @Query("titles") String driverName
    );

    @GET("w/api.php?action=query&prop=pageimages&format=json&formatversion=2&pithumbsize=500")
    Call<RaceImage> getImageRace(
            @Query("titles") String raceName
    );
}
