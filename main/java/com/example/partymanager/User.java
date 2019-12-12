package com.example.partymanager;

import java.util.ArrayList;

public class User {

    private String name;
    private String uid;
    private int drink_count;
    private ArrayList<Event> events;

    public User(String name, String uid, int drink_count, ArrayList<Event> events){
        this.name = name;
        this.uid = uid;
        this.drink_count = drink_count;
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public int getDrink_count() {
        return drink_count;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

}
