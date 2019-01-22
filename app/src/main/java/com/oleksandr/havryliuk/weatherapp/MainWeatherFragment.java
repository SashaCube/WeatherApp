package com.oleksandr.havryliuk.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainWeatherFragment extends Fragment {

    private View view;
    private EditText city;
    private TextView weather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_weather, container, false);

        return view;
    }

    public void initView(){

    }

    public void loadWeather(String city){

    }
}
