package com.example.nanoformula.API;

import com.example.nanoformula.modelo.countryDetails.CountryDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RESTCountriesApi {

    @GET("name/{nombre}")
    Call<List<CountryDetail>> getCountryCode(@Path("nombre") String countryName);
}
