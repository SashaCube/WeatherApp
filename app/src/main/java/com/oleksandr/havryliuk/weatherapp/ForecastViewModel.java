package com.oleksandr.havryliuk.weatherapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.weatherapp.room.MyWeather;

import java.util.List;


public class ForecastViewModel extends AndroidViewModel {


    private Repository repository;
    private final MutableLiveData<String> cityInput = new MutableLiveData<>();
    public final LiveData<List<MyWeather>> forecast =
            Transformations.switchMap(cityInput, (city) -> {
                return repository.getForecast(city);
            });

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
    }

    public LiveData<List<MyWeather>> getForecast(){
        return forecast;
    }

    public void setInput(String city){
        cityInput.setValue(city);
    }
}
