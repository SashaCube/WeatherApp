package com.oleksandr.havryliuk.weatherapp;

import com.oleksandr.havryliuk.weatherapp.api.APIInterface;
import com.oleksandr.havryliuk.weatherapp.api.RetrofitClient;
import com.oleksandr.havryliuk.weatherapp.models.Data;
import com.oleksandr.havryliuk.weatherapp.models.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private APIInterface client;

    public Repository() {
        client = RetrofitClient.getApi(APIInterface.BASE_URL);
    }

    public interface LoadData<T> {
        void onData(T data);

        void onFailure();
    }

    public void loadData(final LoadData<java.util.List<List>> callback, String city) {

        Call<Data> call = client.getWeaterByCity(city, APIInterface.APP_ID);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() == null || response.body().getList() == null) {
                    callback.onFailure();
                    return;
                }
                callback.onData(response.body().getList());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                callback.onFailure();
                t.printStackTrace();
            }
        });
    }
}
