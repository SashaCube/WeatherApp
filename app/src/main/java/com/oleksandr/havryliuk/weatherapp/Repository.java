package com.oleksandr.havryliuk.weatherapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.oleksandr.havryliuk.weatherapp.api.APIInterface;
import com.oleksandr.havryliuk.weatherapp.api.RetrofitClient;
import com.oleksandr.havryliuk.weatherapp.models.Data;
import com.oleksandr.havryliuk.weatherapp.room.MyWeather;
import com.oleksandr.havryliuk.weatherapp.room.MyWeatherDao;
import com.oleksandr.havryliuk.weatherapp.room.WeatherRoomDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Repository {

    private MyWeatherDao myWeatherDao;
    private APIInterface client;
    private Executor executor;

    private static Repository INSTANCE;

    public static Repository getRepository(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
            WeatherRoomDatabase db = WeatherRoomDatabase.getDatabase(application);
            INSTANCE.myWeatherDao = db.myWeatherDao();
            INSTANCE.client = RetrofitClient.getApi(APIInterface.BASE_URL);
            INSTANCE.executor = Executors.newSingleThreadExecutor();
        }
        return INSTANCE;
    }

    public LiveData<List<MyWeather>> getSavedForecast(String city) {
        return myWeatherDao.getWeatherByCity(city);
    }

    public void loadForecast(String city) {

        Call<Data> call = client.getWeaterByCity(city, APIInterface.APP_ID);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() == null) {
                    return;
                }
                Data data = response.body();
                updateForecast(data);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    public void updateForecast(Data data) {
        executor.execute(() -> {
            myWeatherDao.deleteByCity(data.getCity().getName());
            List<MyWeather> list = Helper.weatherConverter(data);
            for (MyWeather w : list) {
                myWeatherDao.insert(w);
            }
        });
    }

    public void clearUserCached() {
        executor.execute(() -> {
            myWeatherDao.deleteAll();
        });
    }
}
