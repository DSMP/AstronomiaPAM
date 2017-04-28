package com.example.damian.astronomiapam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    private static final String EXTRA_MESSAGE_D = "com.example.damian.astronomiapam.MessegeDlugosc";
    private static final String EXTRA_MESSAGE_SZ = "com.example.damian.astronomiapam.MessegeSzerokosc";
    EditText SetDlugosc;
    EditText SetSzerokosc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SetDlugosc = (EditText) findViewById(R.id.setDlugosc);
        SetSzerokosc = (EditText) findViewById(R.id.setSzerokosc);
    }


    public void StartClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE_D, SetDlugosc.getText().toString());
        intent.putExtra(EXTRA_MESSAGE_SZ, SetSzerokosc.getText().toString());
        startActivity(intent);
    }
}
