package com.example.damian.astronomiapam.database;

/**
 * Created by Damian on 09.06.2017.
 */

public class FavouriteEntity {
    private long id;
    private String city;


    public FavouriteEntity(long id, String city) {
        this.id = id;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
