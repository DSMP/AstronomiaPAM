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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_D;
import static com.example.damian.astronomiapam.SettingsActivity.EXTRA_MESSAGE_SZ;

public class MainActivity extends FragmentActivity {


    public double dlugosc = 0;
    public double szerokosc = 0;

    TextView Time;
    TextView longtitude;
    TextView latitude;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent SettingsIntent = getIntent();
        dlugosc = Double.parseDouble(SettingsIntent.getStringExtra(EXTRA_MESSAGE_D));
        szerokosc = Double.parseDouble(SettingsIntent.getStringExtra(EXTRA_MESSAGE_SZ));

        Time = (TextView) findViewById(R.id.Time);
        longtitude = (TextView) findViewById(R.id.Longitude);
        latitude = (TextView) findViewById(R.id.Latitude);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            Calendar c = Calendar.getInstance();
                            @Override
                            public void run() {
                                Time.setText("Czas: " + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) );
                            }
                        });
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
    }

    @Override
    public void onBackPressed() {
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
            if (position == 0)
                fragment = new SunFragment();
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
    }
}
