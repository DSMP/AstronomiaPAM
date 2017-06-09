package com.example.damian.astronomiapam;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.astronomiapam.data.Channel;
import com.example.damian.astronomiapam.data.Condition;
import com.example.damian.astronomiapam.data.LocationResult;
import com.example.damian.astronomiapam.data.Units;
import com.example.damian.astronomiapam.listener.GeocodingServiceListener;
import com.example.damian.astronomiapam.listener.WeatherServiceListener;
import com.example.damian.astronomiapam.service.GoogleMapsGeocodingService;
import com.example.damian.astronomiapam.service.WeatherCacheService;
import com.example.damian.astronomiapam.service.YahooWeatherService;

import static com.example.damian.astronomiapam.R.id.conditionTextView;
import static com.example.damian.astronomiapam.R.id.locationTextView;
import static com.example.damian.astronomiapam.R.id.temperatureTextView;
import static com.example.damian.astronomiapam.R.id.weatherIconImageView;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link WeatherFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link WeatherFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class WeatherFragment extends Fragment {


    public static int GET_WEATHER_FROM_CURRENT_LOCATION = 0x00001;

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView cisnienieLabel;

//    private OnFragmentInteractionListener mListener;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_weather, container, false);

        weatherIconImageView = (ImageView) rootView.findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) rootView.findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) rootView.findViewById(R.id.conditionTextView);
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        cisnienieLabel = (TextView) rootView.findViewById(R.id.cisnienieLabel);
        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    public void loadForecast(int weatherIcon, String temperature, String conditionDesc, String location, Double pressure)
    {
        weatherIconImageView.setImageResource(weatherIcon);
        temperatureTextView.setText(temperature);
        conditionTextView.setText(conditionDesc);
        locationTextView.setText(location);
        cisnienieLabel.setText(String.format("cisnienie: %.2f", pressure));
    }

}
