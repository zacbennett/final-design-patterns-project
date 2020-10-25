package com.example.springboot.classes;

import com.example.springboot.enums.Addresses;
import com.example.springboot.enums.EventTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EventManager {
    private final Db db;
    private final NotificationManager notificationManager;

    public EventManager(Db db, NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
        this.db = db;
    }

    public HashMap<UUID, Event> getAllEvents() {
        return this.db.getInstance().getAllEvents();
    }

    public Event addEvent(String name, Addresses addressEnum, EventTypes eventType) {
        Event newEvent = this.db.getInstance().addEvent(name, addressEnum, eventType);
        this.notificationManager.handleNewUpdatedEvent(newEvent);

        return newEvent;
    }

    public Event getEventById(UUID id) {
        return this.db.getInstance().getEventById(id);
    }

    public Event updateEventById(UUID id, String title, Addresses addressEnum, EventTypes eventType) {
        Event oldEventDetails =  this.db.getInstance().getEventById(id);
        if(oldEventDetails == null) return null;
        EventTypes prevEventType = oldEventDetails.getEventType();
        Addresses prevAddress = oldEventDetails.getAddress();
        Boolean eventOrAddressChanged = (eventType != null && prevEventType != eventType) || (addressEnum != null && prevAddress !=addressEnum);

        Event newEvent = this.db.getInstance().updateEventById(id, title, addressEnum, eventType);

        if(eventOrAddressChanged) {
            this.notificationManager.handleNewUpdatedEvent(newEvent);
        }
        return newEvent;
    }

    public String deleteEventById(UUID uuid) {
        return this.db.getInstance().deleteEventById(uuid);
    }
}
