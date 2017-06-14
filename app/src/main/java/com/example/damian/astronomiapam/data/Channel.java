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

public class Channel implements JSONPopulator {
    private Units units;
    private Item item;
    private String location;
    private Atmosphere atmosphere;

    public Wind getWind() {
        return wind;
    }

    private Wind wind;

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public void populate(JSONObject data) {

        wind = new Wind();
        wind.populate(data.optJSONObject("wind"));

        atmosphere = new Atmosphere();
        atmosphere.populate(data.optJSONObject("atmosphere"));

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

        JSONObject locationData = data.optJSONObject("location");

        String region = locationData.optString("region");
        String country = locationData.optString("country");

        location = String.format("%s, %s", locationData.optString("city"), (region.length() != 0 ? region : country));
    }

    @Override
    public JSONObject toJSON() {

        JSONObject data = new JSONObject();

        try {
            data.put("units", units.toJSON());
            data.put("item", item.toJSON());
            data.put("requestLocation", location);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
//    public static Channel fromByteArray(byte[] bytes)
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
//        Channel r = (Channel) obj;
//
//        return r;
//    }
//
//    public static byte[] toByteArray(Channel r)
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
