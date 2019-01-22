package com.oleksandr.havryliuk.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oleksandr.havryliuk.weatherapp.models.Main;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Repository repository;
    private EditText cityEt;
    private TextView description, cityTv, mainTv, iconTv;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new Repository();
        initView();
    }

    public void initView() {
        cityEt = findViewById(R.id.city_et);
        description = findViewById(R.id.description_tv);
        cityTv = findViewById(R.id.city_tv);
        mainTv = findViewById(R.id.main_tv);
        iconTv = findViewById(R.id.weather_icon);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.loadData(new Repository.LoadData<Main>() {
                    @Override
                    public void onData(Main data) {
                        Log.d(TAG, "onData received");
                        onDataUpdate(data);
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onFailure no data received");
                    }
                }, cityEt.getText().toString());
            }
        });
    }

    private void onDataUpdate(Main data) {
        iconTv.setText(data.getTempMax().toString());
        mainTv.setText(data.getTempMin().toString());
    }
}
