package com.example.damian.astronomiapam.listener;

import com.example.damian.astronomiapam.data.LocationResult;

public interface GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}
