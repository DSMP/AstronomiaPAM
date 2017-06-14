package com.example.damian.astronomiapam.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private int highTemperature;
    private int lowTemperature;
    private String description;
    private String day;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public String getDescription() {
        return description;
    }

    public String getDay() {
        return day;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        highTemperature = data.optInt("high");
        lowTemperature = data.optInt("low");
        description = data.optString("text");
        day = data.optString("day");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("code", code);
            data.put("temp", temperature);
            data.put("high", highTemperature);
            data.put("low", lowTemperature);
            data.put("text", description);
            data.put("day", day);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
//    public static Condition fromByteArray(byte[] bytes)
//            throws StreamCorruptedException, IOException,
//            ClassNotFoundException {
//
//        if (bytes == null) {
//            return null;
//        }
//
//        ObjectInputStream objectIn = new ObjectInputStream(
//                new ByteArrayInputStream(bytes));
//
//        Object obj = objectIn.readObject();
//        Condition r = (Condition) obj;
//
//        return r;
//    }
//
//    public static byte[] toByteArray(Condition r)
//            throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oout = new ObjectOutputStream(baos);
//
//        oout.writeObject(r);
//        oout.close();
//        byte[] buf = baos.toByteArray();
//
//        return buf;
//    }
}
