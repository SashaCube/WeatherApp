package com.oleksandr.havryliuk.weatherapp.api;
import com.oleksandr.havryliuk.weatherapp.api.models.Data;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

    String BASE_URL = "http://api.openweathermap.org/";
    String APP_ID = "98eaf2c433ce1765a54018269be854e8";

    @GET("data/2.5/weather?q={city}&appid={APP_ID}")
    Call<Data> getWeaterByCity(@Path("city") String city, @Path("appid") String appId);
    
}
