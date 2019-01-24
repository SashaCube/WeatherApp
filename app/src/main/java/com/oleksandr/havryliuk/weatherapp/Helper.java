package com.oleksandr.havryliuk.weatherapp;

import com.oleksandr.havryliuk.weatherapp.models.Data;
import com.oleksandr.havryliuk.weatherapp.room.MyWeather;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static List<MyWeather> weatherConverter(Data data){
        java.util.List<MyWeather> weatherList = new ArrayList<>();
        MyWeather myWeather;

        for (com.oleksandr.havryliuk.weatherapp.models.List l : data.getList()) {
            myWeather = new MyWeather(data.getCity().getName(), l);
            weatherList.add(myWeather);
        }

        return weatherList;
    }
}
