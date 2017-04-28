package com.example.damian.astronomiapam;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;


public class MoonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    TextView wschTextVMoon;
    TextView zachTextVMoon;
    TextView NajNowTextVMoon;
    TextView NajPelniaTextVMoon;
    TextView FazaKTextVMoon;
    TextView DzienMieSynodTextVMoon;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_moon, container, false);
        wschTextVMoon = (TextView) rootView.findViewById(R.id.wschTextVMoon);
        zachTextVMoon = (TextView) rootView.findViewById(R.id.zachTextVMoon);
        NajNowTextVMoon = (TextView) rootView.findViewById(R.id.NajNowTextVMoon);
        NajPelniaTextVMoon = (TextView) rootView.findViewById(R.id.NajPelniaTextVMoon);
        FazaKTextVMoon = (TextView) rootView.findViewById(R.id.FazaKTextVMoon);
        DzienMieSynodTextVMoon = (TextView) rootView.findViewById(R.id.DzienMieSynodTextVMoon);

        Calendar cal = Calendar.getInstance();
        AstroDateTime AstroDT = new AstroDateTime(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND),1,false);
        Bundle bundle = new Bundle();
        AstroCalculator astroCalculator = new AstroCalculator(AstroDT, new AstroCalculator.Location(bundle.getDouble("dlugosc"),bundle.getDouble("szerokosc")));

        wschTextVMoon.setText("Wschod: " + astroCalculator.getMoonInfo().getMoonset());
        zachTextVMoon.setText("Zachod: " + astroCalculator.getMoonInfo().getMoonrise());
        NajNowTextVMoon.setText("Najblizszy now: " + astroCalculator.getMoonInfo().getNextNewMoon());
        NajPelniaTextVMoon.setText("Najblizsza pelnia: " + astroCalculator.getMoonInfo().getNextFullMoon());
        FazaKTextVMoon.setText("Faza ksiezyca: " + String.format("%.2f", astroCalculator.getMoonInfo().getIllumination()) + "%");
        DzienMieSynodTextVMoon.setText("Dzien miesiaca Synodycznego: " + (Math.abs(astroCalculator.getMoonInfo().getNextNewMoon().getDay() - cal.get(Calendar.DAY_OF_MONTH))));

        return rootView;
    }
}
