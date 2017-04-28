package com.example.damian.astronomiapam;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.lang.reflect.Array;
import java.util.Date;



public class SunFragment extends Fragment {

    TextView wschTextVSun;
    TextView zachTextVSun;
    TextView ZmieTextVSun;
    TextView switTextVSun;


    @TargetApi(Build.VERSION_CODES.N)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_sun, container, false);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        AstroDateTime AstroDT = new AstroDateTime(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND),1,false);
        Bundle bundle = new Bundle();
        AstroCalculator astroCalculator = new AstroCalculator(AstroDT, new AstroCalculator.Location(bundle.getDouble("dlugosc"),bundle.getDouble("szerokosc")));

        wschTextVSun = (TextView) rootView.findViewById(R.id.wschTextVSun);
        zachTextVSun = (TextView) rootView.findViewById(R.id.zachTextVSun);
        ZmieTextVSun = (TextView) rootView.findViewById(R.id.ZmieTextVSun);
        switTextVSun = (TextView) rootView.findViewById(R.id.switTextVSun);

        wschTextVSun.setText("Wschód: " + astroCalculator.getSunInfo().getSunrise() + " / " + astroCalculator.getSunInfo().getAzimuthRise());
        zachTextVSun.setText("Zachód: " + astroCalculator.getSunInfo().getSunset() + " / " + astroCalculator.getSunInfo().getAzimuthSet());
        ZmieTextVSun.setText("Zmierzch: " + astroCalculator.getSunInfo().getTwilightEvening());
        switTextVSun.setText("Swit: " + astroCalculator.getSunInfo().getTwilightMorning());

        return rootView;
    }

}
