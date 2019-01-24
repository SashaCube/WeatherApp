package com.oleksandr.havryliuk.weatherapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oleksandr.havryliuk.weatherapp.models.Data;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Repository repository;
    private ForecastViewModel forecastViewModel;

    private EditText cityEt;
    private Button button;
    private RecyclerView recyclerView;
    private TextView cityTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = Repository.getRepository();

        forecastViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        forecastViewModel.getForecast().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(@Nullable Data data) {
                onDataUpdate(data);
            }
        });

        initView();
    }

    public void initView() {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        cityEt = findViewById(R.id.city_et);
        cityTv = findViewById(R.id.city_tv);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityEt.getText().toString();

                repository.loadData(new Repository.LoadData<Data>() {
                    @Override
                    public void onData(Data data) {
                        Log.d(TAG, "onData received");
                        repository.setForecast(data);
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onFailure no data received");
                    }
                }, city);
            }
        });
    }

    private void onDataUpdate(Data data) {
        cityTv.setText(data.getCity().getName());

        WeatherAdapter adapter = new WeatherAdapter(data.getList(), this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
