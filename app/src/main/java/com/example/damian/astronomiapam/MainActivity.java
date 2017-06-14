package com.example.damian.astronomiapam;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.astronomiapam.data.Atmosphere;
import com.example.damian.astronomiapam.data.Channel;
import com.example.damian.astronomiapam.data.Condition;
import com.example.damian.astronomiapam.data.LocationResult;
import com.example.damian.astronomiapam.data.Units;
import com.example.damian.astronomiapam.data.Wind;
import com.example.damian.astronomiapam.database.FavouriteEntity;
import com.example.damian.astronomiapam.database.SQLiteAdapter;
import com.example.damian.astronomiapam.listener.GeocodingServiceListener;
import com.example.damian.astronomiapam.listener.WeatherServiceListener;
import com.example.damian.astronomiapam.service.GoogleMapsGeocodingService;
import com.example.damian.astronomiapam.service.WeatherCacheService;
import com.example.damian.astronomiapam.service.YahooWeatherService;

import static com.example.damian.astronomiapam.R.id.conditionTextView;
import static com.example.damian.astronomiapam.R.id.locationTextView;
import static com.example.damian.astronomiapam.R.id.temperatureTextView;
import static com.example.damian.astronomiapam.R.id.weatherIconImageView;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_CF;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_D;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_R;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_R_bool;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_SZ;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_lok;
import static com.example.damian.astronomiapam.WeatherFragment.GET_WEATHER_FROM_CURRENT_LOCATION;

public class MainActivity extends FragmentActivity implements WeatherServiceListener, GeocodingServiceListener, LocationListener {


    public double dlugosc = 0;
    public double szerokosc = 0;
    int refresh = 0;
    int cycles = 0;
    String lokalizacja;
    boolean isRefreshed = false;
    String temperatureSelected;

    TextView Time;
    TextView longtitude;
    TextView latitude;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;
    private static final int NUM_PAGES_WHEATHER = 2;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    private ViewPager mPagerWheather;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private PagerAdapter mPagerAdapterWheather;

    private YahooWeatherService weatherService;
    private GoogleMapsGeocodingService geocodingService;
    private WeatherCacheService cacheService;
    private ProgressDialog loadingDialog;
    private boolean weatherServicesHasFailed = false;

    WeatherFragment weatherFragment;
    private WeatherWindFragment weatherWindFragment;
    private String YahooLocation;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent SettingsIntent = getIntent();
        dlugosc = Double.parseDouble(SettingsIntent.getStringExtra(EXTRA_MESSAGE_D));
        szerokosc = Double.parseDouble(SettingsIntent.getStringExtra(EXTRA_MESSAGE_SZ));
        refresh = Integer.parseInt(SettingsIntent.getStringExtra(EXTRA_MESSAGE_R));
        lokalizacja = SettingsIntent.getStringExtra(EXTRA_MESSAGE_lok);
        isRefreshed = SettingsIntent.getBooleanExtra(EXTRA_MESSAGE_R_bool, false);
        temperatureSelected = SettingsIntent.getStringExtra(EXTRA_MESSAGE_CF);

        Time = (TextView) findViewById(R.id.Time);
        longtitude = (TextView) findViewById(R.id.Longitude);
        latitude = (TextView) findViewById(R.id.Latitude);

        longtitude.setText(dlugosc + "");
        latitude.setText(szerokosc + "");

        weatherService = new YahooWeatherService(this);
        weatherService.setTemperatureUnit(temperatureSelected);

        geocodingService = new GoogleMapsGeocodingService(this);
        cacheService = new WeatherCacheService(this);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            Calendar c = Calendar.getInstance();
                            @Override
                            public void run() {
                                Time.setText("Czas: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) );
                                cycles++;
                                if (cycles == refresh*60 || isRefreshed)
                                {
                                    RefreshFragments();
                                    isRefreshed = false;
                                    cycles = 0;
                                }
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPagerWheather = (ViewPager) findViewById(R.id.wheatherPager);
        mPagerAdapterWheather = new ScreenSlideWheatherPagerAdapter(getSupportFragmentManager());
        mPagerWheather.setAdapter(mPagerAdapterWheather);

    }
    @Override
    protected void onStart() {
        super.onStart();

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.loading));
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        String location = null;

//        if (false) {
//            String locationCache = preferences.getString(getString(R.string.pref_cached_location), null);
//
//            if (locationCache == null) {
//                getWeatherFromCurrentLocation();
//            } else {
//                location = locationCache;
//            }
//        } else {
//        }

        location = lokalizacja;
        if (location != null) {
            weatherService.refreshWeather(location);
        }
    }

    private void RefreshFragments() {
        try {
            ((ScreenSlidePagerAdapter)mPagerAdapter).MyFinalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        int a = ((ScreenSlidePagerAdapter)mPager.getAdapter()).getCount();


        try {
            ((ScreenSlideWheatherPagerAdapter)mPagerAdapterWheather).MyFinalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        mPagerAdapterWheather = new ScreenSlideWheatherPagerAdapter(getSupportFragmentManager());
        mPagerWheather.setAdapter(mPagerAdapterWheather);
        String location = null;
        location = lokalizacja;
        if (location != null) {
            weatherService.refreshWeather(location);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        super.onBackPressed();
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
    }

    public void SettingsClicked(View view) {
        this.onBackPressed();
    }


    private void getWeatherFromCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
            }, GET_WEATHER_FROM_CURRENT_LOCATION);

            return;
        }

        // system's LocationManager
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Criteria locationCriteria = new Criteria();

        if (isNetworkEnabled) {
            locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        } else if (isGPSEnabled) {
            locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        }

        locationManager.requestSingleUpdate(locationCriteria, this, null);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == GET_WEATHER_FROM_CURRENT_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherFromCurrentLocation();
            } else {
                loadingDialog.hide();

                AlertDialog messageDialog = new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.location_permission_needed)).create();
//                        .setPositiveButton(getString(R.string.disable_geolocation), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                startSettingsActivity();
//                            }
//                        })
//                        .create();

                messageDialog.show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        geocodingService.refreshLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void serviceSuccess(Channel channel) {
        loadingDialog.hide();
        YahooLocation = channel.getLocation();

        Condition condition = channel.getItem().getCondition();
        Units units = channel.getUnits();
        Wind wind = channel.getWind();
        Atmosphere atmosphere = channel.getAtmosphere();
        Condition[] forecast = channel.getItem().getForecast();

        int weatherIconImageResource = getResources().getIdentifier("icon_" + condition.getCode(), "drawable", getPackageName());

//        weatherIconImageView.setImageResource(weatherIconImageResource);
//        temperatureTextView.setText(getString(R.string.temperature_output, condition.getTemperature(), units.getTemperature()));
//        conditionTextView.setText(condition.getDescription());
//        locationTextView.setText(channel.getLocation());
        String temperature = getString(R.string.temperature_output, condition.getTemperature(), units.getTemperature());
//        int weather = getResources().getIdentifier(weatherFragment.getId()+"","id",getPackageName());
        weatherFragment.loadForecast(weatherIconImageResource, temperature, condition.getDescription(), channel.getLocation(), atmosphere.getPressure());
        weatherWindFragment.loadForecast(weatherIconImageResource,wind.getSpeed(), wind.getDirection(), atmosphere.getHumidity(), atmosphere.getVisibility());

        for (int day = 0; day < forecast.length; day++) {
            if (day >= 5) {
                break;
            }

            Condition currentCondition = forecast[day];

            int viewId = getResources().getIdentifier("forecast_" + day, "id", getPackageName());
//            WeatherConditionFragment fragment = (WeatherConditionFragment) getSupportFragmentManager().findFragmentById(viewId);
            WeatherConditionFragment fragment = (WeatherConditionFragment) weatherFragment.getChildFragmentManager().findFragmentById(viewId);
            if (fragment != null) {
                fragment.loadForecast(currentCondition, channel.getUnits());
            }
        }

        cacheService.save(channel);
    }

    @Override
    public void serviceFailure(Exception exception) {
        // display error if this is the second failure
        if (weatherServicesHasFailed) {
            loadingDialog.hide();
            Toast.makeText(this,"brak internetu", Toast.LENGTH_LONG).show(); // exception.getMessage()
        } else {
            // error doing reverse geocoding, load weather data from cache
            weatherServicesHasFailed = true;
            // OPTIONAL: let the user know an error has occurred then fallback to the cached data
            Toast.makeText(this, "brak danych w pliku", Toast.LENGTH_SHORT).show(); // exception.getMessage()

            cacheService.load(this);
        }
    }

    @Override
    public void geocodeSuccess(LocationResult location) {
        // completed geocoding successfully
        weatherService.refreshWeather(location.getAddress());

//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(getString(R.string.pref_cached_location), location.getAddress());
//        editor.apply();
    }

    @Override
    public void geocodeFailure(Exception exception) {
        // GeoCoding failed, try loading weather data from the cache
        cacheService.load(this);
    }

    public void saveTofavourite(View view) {
        SQLiteAdapter sqLiteAdapter = new SQLiteAdapter(getApplicationContext());
        sqLiteAdapter.open();
        sqLiteAdapter.insert(new FavouriteEntity(0,YahooLocation));
        sqLiteAdapter.close();
        Toast.makeText(this, "Saved current town", Toast.LENGTH_SHORT).show();
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            Bundle bundle = new Bundle();
            bundle.putDouble("dlugosc", dlugosc);
            bundle.putDouble("szerokosc", szerokosc);
            if (position == 0) {
                fragment = new SunFragment();
            }
            else
            {
                fragment = new MoonFragment();
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
        public void MyFinalize() throws Throwable {
            finalize();
        }
    }
    private class ScreenSlideWheatherPagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlideWheatherPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            Bundle bundle = new Bundle();
//            bundle.putString("lokacja", lokalizacja);
//            bundle.putDouble("szerokosc", szerokosc);
            if (position == 0) {
                fragment = new WeatherFragment(); // nowe fragmenty
                weatherFragment = (WeatherFragment) fragment;
            }
            else
            {
                fragment = new WeatherWindFragment();
                weatherWindFragment = (WeatherWindFragment) fragment;
            }
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES_WHEATHER;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
        public void MyFinalize() throws Throwable {
            finalize();
        }
    }
}
