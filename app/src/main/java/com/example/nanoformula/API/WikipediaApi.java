package com.example.nanoformula.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikipediaApi {
//http://en.wikipedia.org/w/api.php?action=query&titles=Lando_Norris&prop=pageimages&format=json&pithumbsize=100
    @GET("w/api.php")
    Call<> getImageDriver(
        @Query("action") String key,
        @Query("titles") String driverName,
        @Query("prop") String prop,
        @Query("format") String format,
        @Query("pithumbsize") int pithumbsize
    );
}
