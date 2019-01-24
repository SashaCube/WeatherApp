package com.oleksandr.havryliuk.weatherapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oleksandr.havryliuk.weatherapp.room.MyWeather;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ForecastViewModel forecastViewModel;

    private EditText cityEt;
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

        forecastViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        forecastViewModel.getForecast().observe(this, data -> {
            onDataUpdate(data);
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        cityEt = findViewById(R.id.city_et);
        cityTv = findViewById(R.id.city_tv);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            String city = cityEt.getText().toString();
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
