package com.example.damian.astronomiapam.data;

import org.json.JSONObject;

/**
 * Created by Damian on 09.06.2017.
 */

public class Atmosphere implements JSONPopulator{
    Double pressure;
    Integer humidity;
    Double visibility;

    public Double getVisibility() {
        return visibility;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
    @Override
    public void populate(JSONObject data) {
        pressure = data.optDouble("pressure") / 33.863179;
        humidity = data.optInt("humidity");
        visibility = data.optDouble("visibility");
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
