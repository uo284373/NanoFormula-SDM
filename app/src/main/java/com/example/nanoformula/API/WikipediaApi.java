package com.example.nanoformula.API;

import com.example.nanoformula.modelo.driversImage.DriverImage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WikipediaApi {
//http://en.wikipedia.org/w/api.php?action=query&titles=Lando_Norris&prop=pageimages&format=json&formatversion=2
    @GET("w/api.php?action=query&prop=pageimages&format=json&formatversion=2&pithumbsize=500")
    Call<DriverImage> getImageDriver(
        @Query("titles") String driverName
    );
}
