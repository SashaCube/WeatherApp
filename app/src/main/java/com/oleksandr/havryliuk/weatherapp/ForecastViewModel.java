package com.oleksandr.havryliuk.weatherapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.weatherapp.models.Data;


public class ForecastViewModel extends AndroidViewModel {

    private LiveData<Data> forecast;

    public ForecastViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Data> getForecast(){
        if(forecast == null){
            forecast = Repository.getRepository().getForecast();
        }
        return forecast;
    }
}
