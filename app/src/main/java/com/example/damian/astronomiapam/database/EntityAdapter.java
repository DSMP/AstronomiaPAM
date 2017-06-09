package com.example.damian.astronomiapam.database;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.damian.astronomiapam.R;

import java.util.List;

/**
 * Created by Damian on 09.06.2017.
 */

public class EntityAdapter extends ArrayAdapter<FavouriteEntity> {
    private Activity context;
    private List<FavouriteEntity> entities;
    public EntityAdapter(@NonNull Context context, List<FavouriteEntity> entities) {
        super(context, R.layout.activity_settings , entities);
        this.context = (Activity) context;
        this.entities = entities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
