package com.oleksandr.havryliuk.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oleksandr.havryliuk.weatherapp.room.MyWeather;

import java.text.DecimalFormat;
import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private java.util.List<MyWeather> lists;
    private Context context;

    public WeatherAdapter(List<MyWeather> articles, Context context) {
        this.lists = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final MyViewHolder viewHolder = myViewHolder;

        MyWeather model = lists.get(i);

        viewHolder.time.setText(Helper.dateTimeConverter(model.getTime()));
        viewHolder.main.setText(model.getMain());

        DecimalFormat df = new DecimalFormat("#.##");
        viewHolder.temp.setText(df.format(Helper.kelvinToCelsius(model.getTemp())) + "°C");

        viewHolder.humidity.setText(model.getHumidity() + "%");
        viewHolder.cloudiness.setText(model.getCloudiness() + "%");
        viewHolder.pressure.setText(model.getPressure().toString());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView time, main, temp, humidity, cloudiness, pressure;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            main = itemView.findViewById(R.id.main);
            temp = itemView.findViewById(R.id.temp);
            humidity = itemView.findViewById(R.id.humidity);
            cloudiness = itemView.findViewById(R.id.cloudiness);
            pressure = itemView.findViewById(R.id.pressure);
        }
    }

    public void clear() {
        final int size = lists.size();
        lists.clear();
        notifyItemRangeRemoved(0, size);
    }
}
