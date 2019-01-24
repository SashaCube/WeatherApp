package com.oleksandr.havryliuk.weatherapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
;
import com.oleksandr.havryliuk.weatherapp.api.APIInterface;
import com.oleksandr.havryliuk.weatherapp.api.RetrofitClient;
import com.oleksandr.havryliuk.weatherapp.models.Data;
import com.oleksandr.havryliuk.weatherapp.models.List;
import com.oleksandr.havryliuk.weatherapp.room.MyWeather;
import com.oleksandr.havryliuk.weatherapp.room.MyWeatherDao;
import com.oleksandr.havryliuk.weatherapp.room.WeatherRoomDatabase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private APIInterface client;
    private MyWeatherDao myWeatherDao;
    private LiveData<java.util.List<MyWeather>> forecast;

    private static Repository INSTANCE;

    public static Repository getRepository(Application application){
        if(INSTANCE == null) {
            INSTANCE = new Repository();
            WeatherRoomDatabase db = WeatherRoomDatabase.getDatabase(application);
            INSTANCE.myWeatherDao = db.myWeatherDao();
            INSTANCE.client = RetrofitClient.getApi(APIInterface.BASE_URL);
        }
        return INSTANCE;
    }

    public void insert(MyWeather weather) {
        new insertAsyncTask(myWeatherDao).execute(weather);
    }

    private static class insertAsyncTask extends AsyncTask<MyWeather, Void, Void> {

        private MyWeatherDao mAsyncTaskDao;

        insertAsyncTask(MyWeatherDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MyWeather... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public LiveData<java.util.List<MyWeather>> getForecast(String city){
        forecast = myWeatherDao.getWeatherByCity(city);
        return forecast;
    }

    public void loadData(final String city) {

            Call<Data> call = client.getWeaterByCity(city, APIInterface.APP_ID);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.body() == null) {
                        return;
                    }
                    Data data = response.body();
                    java.util.List<MyWeather> weatherList = new ArrayList<>();
                    MyWeather myWeather;

                    for (List l : data.getList()) {
                        myWeather = new MyWeather(data.getCity().getName(), l);
                        insert(myWeather);
                        weatherList.add(myWeather);
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {

                }
            });
    }
}
