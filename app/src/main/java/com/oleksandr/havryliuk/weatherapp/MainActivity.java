package com.oleksandr.havryliuk.weatherapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.oleksandr.havryliuk.weatherapp.room.MyWeather;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ForecastViewModel forecastViewModel;

    private AutoCompleteTextView cityAutoComplete;
    private Button button;
    private RecyclerView recyclerView;
    private TextView cityTv;
    private WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView() {

        // viewModel
        forecastViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        forecastViewModel.getForecast().observe(this, data -> {
            onDataUpdate(data);
        });

        // autocomplete
        cityAutoComplete = findViewById(R.id.autocomplete_cities);
        String[] cities = getResources().getStringArray(R.array.cities_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        cityAutoComplete.setAdapter(adapter);

        // recyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        cityTv = findViewById(R.id.city_tv);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            String city = cityAutoComplete.getText().toString();
            forecastViewModel.setInput(city);
        });
    }

    private void onDataUpdate(List<MyWeather> data) {
        if(data == null || data.isEmpty()){
            if(adapter != null) {
                adapter.clear();
            }
            cityTv.setText("...");
            return;
        }
        cityTv.setText(data.get(0).getCity());

        adapter = new WeatherAdapter(data, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
