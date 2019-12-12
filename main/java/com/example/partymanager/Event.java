package com.example.partymanager;

import java.io.Serializable;
import java.util.ArrayList;

public class Event implements Serializable {

    private String title;
    private String location;
    private String host;
    private String date;
    private ArrayList<User> guests;
    private String details;

    public Event(String title, String location, String date, String host, ArrayList<User> guests, String details){
        this.title = title;
        this.date = date;
        this.location = location;
        this.host = host;
        this.guests = guests;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<User> getGuests() {
        return guests;
    }

    public String getHost() {
        return host;
    }

    public String getDetails() {
        return details;
    }
}
