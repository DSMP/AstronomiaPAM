package com.example.damian.astronomiapam;

import android.content.EntityIterator;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.damian.astronomiapam.database.EntityAdapter;
import com.example.damian.astronomiapam.database.FavouriteEntity;
import com.example.damian.astronomiapam.database.SQLiteAdapter;
import com.example.damian.astronomiapam.listener.DBSpinnerListener;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static final String EXTRA_MESSAGE_D = "com.example.damian.astronomiapam.MessegeDlugosc";
    static final String EXTRA_MESSAGE_SZ = "com.example.damian.astronomiapam.MessegeSzerokosc";
    static final String EXTRA_MESSAGE_R = "com.example.damian.astronomiapam.MessegeRefresh";
    static final String EXTRA_MESSAGE_lok = "com.example.damian.astronomiapam.lokalizacja";
    static final String EXTRA_MESSAGE_R_bool = "com.example.damian.astronomiapam.refreshed";
    static final String EXTRA_MESSAGE_CF = "com.example.damian.astronomiapam.selectedCF";
    EditText SetDlugosc;
    EditText SetSzerokosc;
    EditText SetRefresh;
    EditText SetLokalizacja;
    Spinner temperatureListSpinner;
    boolean refreshed = false;
    String temperatureSelected;
    Spinner FavouriteListSpinner;
    SQLiteAdapter sqLiteAdapter;
    ArrayList<String> favouriteEntities;
    Cursor cursor;
    private DBSpinnerListener dbListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SetDlugosc = (EditText) findViewById(R.id.setDlugosc);
        SetSzerokosc = (EditText) findViewById(R.id.setSzerokosc);
        SetRefresh = (EditText) findViewById(R.id.setRefresh);
        SetLokalizacja = (EditText) findViewById(R.id.lokalizacjaEntry);
        temperatureListSpinner = (Spinner) findViewById(R.id.temperatureListSpinner);
        FavouriteListSpinner = (Spinner) findViewById(R.id.FavouriteListSpinner);
        SetDlugosc.setText("51.760815");
        SetSzerokosc.setText("19.432903");
        SetRefresh.setText("15");
        SetLokalizacja.setText("lodz, PL");


        sqLiteAdapter = new SQLiteAdapter(getApplicationContext());
        sqLiteAdapter.open();
        favouriteEntities = (ArrayList<String>) sqLiteAdapter.printDB();


        FavouriteListSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,favouriteEntities));
        dbListener = new DBSpinnerListener();
        FavouriteListSpinner.setOnItemSelectedListener(this);
        sqLiteAdapter.close();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.temperature_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureListSpinner.setAdapter(adapter);
        temperatureListSpinner.setOnItemSelectedListener(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sqLiteAdapter.open();
        favouriteEntities = (ArrayList<String>) sqLiteAdapter.printDB();
        FavouriteListSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,favouriteEntities));
        sqLiteAdapter.close();

    }

    public void StartClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE_D, EmptyChecker(SetDlugosc.getText().toString(), "0"));
        intent.putExtra(EXTRA_MESSAGE_SZ, EmptyChecker(SetSzerokosc.getText().toString(), "0"));
        intent.putExtra(EXTRA_MESSAGE_R, EmptyChecker(SetRefresh.getText().toString(), "1"));
        intent.putExtra(EXTRA_MESSAGE_lok, EmptyChecker(SetLokalizacja.getText().toString(),"city, XY"));
        intent.putExtra(EXTRA_MESSAGE_R_bool, refreshed);
        intent.putExtra(EXTRA_MESSAGE_CF, temperatureSelected);
        startActivity(intent);
    }
    private String EmptyChecker(String s, String defValue)
    {
        return s.isEmpty() ? defValue : s;
    }

    public void RefreshClicked(View view) {
        refreshed = true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.temperatureListSpinner) {
            if (position == 0) {
                temperatureSelected = "c";
            } else if (position == 1) {
                temperatureSelected = "f";
            } else temperatureSelected = "c";
        }
        else if (parent.getId() == R.id.FavouriteListSpinner)
        {
            SetLokalizacja.setText(favouriteEntities.get(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
