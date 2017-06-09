package com.example.damian.astronomiapam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class WeatherWindFragment extends Fragment {

    TextView speedTextView;
    TextView directionTextView;
    TextView humidityTextView;
    TextView visibilityTextView;
    private ImageView weatherIconImageView;

    public WeatherWindFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup views = (ViewGroup) inflater.inflate(R.layout.fragment_weather_wind, container, false);
        weatherIconImageView = (ImageView) views.findViewById(R.id.weatherIconImageView);
        speedTextView = (TextView) views.findViewById(R.id.windSpeedTextView);
        directionTextView = (TextView) views.findViewById(R.id.windDirectionTextView);
        humidityTextView = (TextView) views.findViewById(R.id.humidityTextView);
        visibilityTextView = (TextView) views.findViewById(R.id.visibilityTextView);
        // Inflate the layout for this fragment
        return views;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void loadForecast(int weatherIcon, int windSpeed, int windDirection, Integer humidity, Double visibility)
    {
        weatherIconImageView.setImageResource(weatherIcon);
        speedTextView.setText(String.format("Wind Speed: %d", windSpeed));
        directionTextView.setText(String.format("Wind direction: %d", windDirection));
        humidityTextView.setText(String.format("humidity: %d", humidity));
        visibilityTextView.setText(String.format("cisnienie: %.2f", visibility));
    }

}
