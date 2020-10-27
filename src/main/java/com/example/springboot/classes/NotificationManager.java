package com.example.springboot.classes;

import com.example.springboot.enums.Addresses;
import com.example.springboot.enums.EventTypes;

import java.util.ArrayList;

public class NotificationManager {
    private final Db db;

    public NotificationManager(Db db) {
        this.db = db;
    }

    public void handleNewUpdatedEvent(Event event) {
        Addresses eventAddress = event.getAddress();
        EventTypes eventType = event.getEventType();

        ArrayList<User> interestedUsers = this.db.getInstance().getInterestedUsersByAddress(eventAddress, eventType);

        for (User user : interestedUsers) {
            String message = this.getNotificationMessage(event);
            user.notifyUser(message);
        }
    }

    public void handleNewUpdatedUser(User user) {
        Addresses userAddress = user.getAddress();
        ArrayList<EventTypes> eventTypes = user.getEventType();

        // returns all the interesting events in the address
        ArrayList<Event> interestedEvents = this.db.getInstance().getInterestedEventsByAddress(userAddress, eventTypes);

        for (Event event : interestedEvents) {
            String message = this.getNotificationMessage(event);
            user.notifyUser(message);
        }
    }
    private String getNotificationMessage(Event event) {
        String eventTitle = event.getTitle();
        return "Check out this event: " + eventTitle;
    }
}
