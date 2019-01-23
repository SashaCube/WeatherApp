package com.oleksandr.havryliuk.weatherapp;

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
import com.oleksandr.havryliuk.weatherapp.models.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Repository repository;
    private EditText cityEt;
    private Button button;
    private RecyclerView recyclerView;
    private TextView cityTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new Repository();
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
                cityTv.setText(city);

                repository.loadData(new Repository.LoadData<java.util.List<List>>() {
                    @Override
                    public void onData(java.util.List<List> data) {
                        Log.d(TAG, "onData received");
                        onDataUpdate(data);
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onFailure no data received");
                    }
                }, city);
            }
        });
    }

    private void onDataUpdate(java.util.List<List> data) {
        WeatherAdapter adapter = new WeatherAdapter(data, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
