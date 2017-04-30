package com.example.damian.astronomiapam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    static final String EXTRA_MESSAGE_D = "com.example.damian.astronomiapam.MessegeDlugosc";
    static final String EXTRA_MESSAGE_SZ = "com.example.damian.astronomiapam.MessegeSzerokosc";
    static final String EXTRA_MESSAGE_R = "com.example.damian.astronomiapam.MessegeRefresh";
    EditText SetDlugosc;
    EditText SetSzerokosc;
    EditText SetRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SetDlugosc = (EditText) findViewById(R.id.setDlugosc);
        SetSzerokosc = (EditText) findViewById(R.id.setSzerokosc);
        SetRefresh = (EditText) findViewById(R.id.setRefresh);
        SetDlugosc.setText("51.760815");
        SetSzerokosc.setText("19.432903");
        SetRefresh.setText("15");
    }


    public void StartClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE_D, SetDlugosc.getText().toString());
        intent.putExtra(EXTRA_MESSAGE_SZ, SetSzerokosc.getText().toString());
        intent.putExtra(EXTRA_MESSAGE_R, SetRefresh.getText().toString());
        startActivity(intent);
    }
}
