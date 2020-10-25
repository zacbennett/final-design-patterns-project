package com.example.springboot.classes;
import com.example.springboot.enums.*;

import java.util.UUID;

public class Event {
    private UUID id;
    private String title;
    private Addresses address;
    private EventTypes eventType;

    public Event(String title, Addresses address, EventTypes eventType) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.address = address;
        this.eventType = eventType;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    public EventTypes getEventType() {
        return eventType;
    }

    public void setEventType(EventTypes eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address=" + address +
                ", eventType=" + eventType +
                '}';
    }
}
