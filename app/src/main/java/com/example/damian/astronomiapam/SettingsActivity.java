package com.example.damian.astronomiapam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {
    static final String EXTRA_MESSAGE_D = "com.example.damian.astronomiapam.MessegeDlugosc";
    static final String EXTRA_MESSAGE_SZ = "com.example.damian.astronomiapam.MessegeSzerokosc";
    static final String EXTRA_MESSAGE_R = "com.example.damian.astronomiapam.MessegeRefresh";
    EditText SetDlugosc;
    EditText SetSzerokosc;
    EditText SetRefresh;
    EditText SetLokalizacja;
    Spinner temperatureListSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SetDlugosc = (EditText) findViewById(R.id.setDlugosc);
        SetSzerokosc = (EditText) findViewById(R.id.setSzerokosc);
        SetRefresh = (EditText) findViewById(R.id.setRefresh);
        SetLokalizacja = (EditText) findViewById(R.id.lokalizacjaEntry);
        temperatureListSpinner = (Spinner) findViewById(R.id.temperatureListSpinner);
        SetDlugosc.setText("51.760815");
        SetSzerokosc.setText("19.432903");
        SetRefresh.setText("15");
        SetLokalizacja.setText("lodz, PL");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.temperature_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureListSpinner.setAdapter(adapter);
    }


    public void StartClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE_D, EmptyChecker(SetDlugosc.getText().toString(), "0"));
        intent.putExtra(EXTRA_MESSAGE_SZ, EmptyChecker(SetSzerokosc.getText().toString(), "0"));
        intent.putExtra(EXTRA_MESSAGE_R, EmptyChecker(SetRefresh.getText().toString(), "1"));
        startActivity(intent);
    }
    private String EmptyChecker(String s, String defValue)
    {
        return s.isEmpty() ? defValue : s;
    }


}
