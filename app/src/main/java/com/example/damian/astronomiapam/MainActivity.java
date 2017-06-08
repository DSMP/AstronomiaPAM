package com.example.damian.astronomiapam;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_D;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_R;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_R_bool;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_SZ;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_lok;

public class MainActivity extends FragmentActivity {


    public double dlugosc = 0;
    public double szerokosc = 0;
    int refresh = 0;
    int cycles = 0;
    String lokalizacja;
    boolean isRefreshed = false;

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

        Time = (TextView) findViewById(R.id.Time);
        longtitude = (TextView) findViewById(R.id.Longitude);
        latitude = (TextView) findViewById(R.id.Latitude);

        longtitude.setText(dlugosc + "");
        latitude.setText(szerokosc + "");

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
            bundle.putDouble("dlugosc", dlugosc);
            bundle.putDouble("szerokosc", szerokosc);
            if (position == 0) {
                fragment = new WeatherFragment(); // nowe fragmenty
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
