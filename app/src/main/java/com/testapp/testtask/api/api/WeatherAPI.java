package com.testapp.testtask.api.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("v2.0/current")
    Call<JsonElement> getWeather(@Query("city") String name,
                                 @Query("key") String apiKey);
}
