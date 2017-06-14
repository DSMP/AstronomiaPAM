package com.example.damian.astronomiapam.listener;

import com.example.damian.astronomiapam.data.Channel;

public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
