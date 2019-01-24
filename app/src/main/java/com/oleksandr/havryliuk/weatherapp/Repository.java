package com.oleksandr.havryliuk.weatherapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.oleksandr.havryliuk.weatherapp.api.APIInterface;
import com.oleksandr.havryliuk.weatherapp.api.RetrofitClient;
import com.oleksandr.havryliuk.weatherapp.models.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private APIInterface client;
    private MutableLiveData<Data> forecast;

    private static Repository INSTANCE;

    public static Repository getRepository(){
        if(INSTANCE == null){
            INSTANCE = new Repository();
            INSTANCE.client = RetrofitClient.getApi(APIInterface.BASE_URL);
            INSTANCE.forecast = new MutableLiveData<>();
        }
        return INSTANCE;
    }
    public interface LoadData<T> {
        void onData(T data);

        void onFailure();
    }

    public LiveData<Data> getForecast(){
        return forecast;
    }

    public void setForecast(Data data){
        forecast.setValue(data);
    }

    public void loadData(final LoadData<Data> callback, String city) {

        Call<Data> call = client.getWeaterByCity(city, APIInterface.APP_ID);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() == null) {
                    callback.onFailure();
                    return;
                }
                callback.onData(response.body());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}
