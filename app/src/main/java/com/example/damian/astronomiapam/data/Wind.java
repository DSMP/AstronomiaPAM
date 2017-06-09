package com.example.damian.astronomiapam.data;

import org.json.JSONObject;

/**
 * Created by Damian on 09.06.2017.
 */

public class Wind implements JSONPopulator {
    int speed;
    int direction;

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public void populate(JSONObject data) {
        speed = data.optInt("speed");
        direction = data.optInt("direction");
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
