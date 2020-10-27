package com.example.springboot.classes;

import com.example.springboot.enums.Addresses;
import com.example.springboot.enums.EventTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Db {
    private HashMap<UUID, User> userTable = new HashMap<>();
    private HashMap<UUID, Event> eventTable = new HashMap<>();;

    //    singleton
    private static Db instance = null;
    public static synchronized Db getInstance() {
        if (instance == null) {
            instance = new Db();
        }
        return instance;
    }

    public User getUserById(UUID id) {
        return this.userTable.get(id);
    }
    public HashMap<UUID, User> getAllUsers() {
        return this.userTable;
    }
    public User addUser(String name, Addresses addressEnum, ArrayList<EventTypes> userEnteredEventInterestsArr) {
        User user = new User(name, addressEnum, userEnteredEventInterestsArr);
        UUID id = user.getId();
        this.userTable.put(id, user);
        return user;
    }

    public String deleteUserById(UUID id) {
        if(this.userTable.containsKey(id)) {
            this.userTable.remove(id);
            return "User deleted";
        } else {
            return "User not found!";
        }
    }

    public User updateUserById(UUID id, String name, Addresses addressEnum, ArrayList<EventTypes> userEnteredEventInterestsArr) {
        //get user
        if(this.userTable.containsKey(id)) {
            User updatedUser = this.userTable.get(id);
            if(name != null) {
                updatedUser.setName(name);
            }
            if(addressEnum != null) {
                updatedUser.setAddress(addressEnum);
            }
            if(userEnteredEventInterestsArr != null) {
                updatedUser.setEventType(userEnteredEventInterestsArr);
            }
            this.userTable.put(id, updatedUser);
            return updatedUser;
        } else {
            return null;
        }
    }

    // event operations
    public Event getEventById(UUID id) {
        return this.eventTable.get(id);
    }
    public HashMap<UUID, Event> getAllEvents() {
        return this.eventTable;
    }
    public Event addEvent(String name, Addresses addressEnum, EventTypes eventTypes) {
        Event event = new Event(name, addressEnum, eventTypes);
        UUID id = event.getId();
        this.eventTable.put(id, event);
        return event;
    }

    public String deleteEventById(UUID id) {
        if(this.eventTable.containsKey(id)) {
            this.eventTable.remove(id);
            return "Event deleted";
        } else {
            return "Event not found!";
        }
    }

    public Event updateEventById(UUID id, String title, Addresses addressEnum, EventTypes eventType) {
        //get event
        if(this.eventTable.containsKey(id)) {
            Event updatedEvent = this.eventTable.get(id);
            if(title != null) {
                updatedEvent.setTitle(title);
            }
            if(addressEnum != null) {
                updatedEvent.setAddress(addressEnum);
            }
            if(eventType != null) {
                updatedEvent.setEventType(eventType);
            }
            this.eventTable.put(id, updatedEvent);
            return updatedEvent;
        } else {
            return null;
        }
    }
    public ArrayList<User> getInterestedUsersByAddress(Addresses address, EventTypes eventTypes) {
        HashMap<UUID,User> allUsers = this.getAllUsers();
        ArrayList<User> results = new ArrayList<>();
        for (Map.Entry<UUID, User> entry : allUsers.entrySet()) {
            User user = entry.getValue();
            Boolean sameAddress = user.getAddress().equals(address);
            Boolean interestedInEvent = user.getEventType().contains(eventTypes);

            if(sameAddress && interestedInEvent) {
                results.add(user);
            }
        }
        return results;
    }

    public ArrayList<Event> getInterestedEventsByAddress(Addresses userAddress, ArrayList<EventTypes> userEventTypes) {
        HashMap<UUID,Event> allEvents = this.getAllEvents();
        ArrayList<Event> results = new ArrayList<>();
        for (Map.Entry<UUID, Event> entry : allEvents.entrySet()) {
            Event event = entry.getValue();
            Boolean sameAddress = event.getAddress().equals(userAddress);
            Boolean interestedInEvent = userEventTypes.contains(event.getEventType());

            if(sameAddress && interestedInEvent) {
                results.add(event);
            }
        }
        return results;
    }
}
