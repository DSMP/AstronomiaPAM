package com.example.damian.astronomiapam.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Damian on 09.06.2017.
 */

public class DBSpinnerListener implements AdapterView.OnItemSelectedListener {
    int position;

    public int getPosition() {
        return position;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
