package com.example.springboot.classes;
import com.example.springboot.enums.*;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private Addresses address;
    private ArrayList<EventTypes> eventType;

    public User(String name, Addresses address, ArrayList<EventTypes> eventType ) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.eventType = eventType;
    }

    public void notifyUser(String message) {
        System.out.print("\n Dear " + this.getName() + ", " + message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    public ArrayList<EventTypes> getEventType() {
        return eventType;
    }

    public void setEventType(ArrayList<EventTypes> eventType) {
        this.eventType = eventType;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", eventType=" + eventType +
                '}';
    }
}
