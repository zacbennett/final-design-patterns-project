package com.example.springboot.helper;

import com.example.springboot.classes.Event;
import com.example.springboot.classes.User;
import com.example.springboot.enums.Addresses;
import com.example.springboot.enums.EventTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Helper {

    // Helper functions for user route
    public static UUID getUserIDFromPayload(HashMap<String,Object> payload) {
        String idAsString = (String) payload.get("id");
        return UUID.fromString(idAsString);
    }
    public static String getUserNameFromPayload(HashMap<String,Object> payload) {
        try {
            return (String) payload.get("name");
        } catch (Exception e) {
            return null;
        }
    }
    public static Addresses getAddressFromPayload(HashMap<String,Object> payload) {
        try {
            String userEnteredAddress = ((String) payload.get("address")).toUpperCase();
            Addresses addressEnum = Addresses.valueOf(userEnteredAddress);
            return addressEnum;
        } catch (Exception e) {
            return null;
        }
    }
    public static ArrayList<EventTypes> getUserEventTypesFromPayload(HashMap<String,Object> payload) {
        try {
            // expect eventType to be csv
            String[] userEnteredEventInterests = ((String) payload.get("eventType")).split(",");
            ArrayList<EventTypes> userEnteredEventInterestsArr = new ArrayList<>();

            // convert user entered strings to enums
            for (String eventType: userEnteredEventInterests) {
                userEnteredEventInterestsArr.add(EventTypes.valueOf(eventType.trim().toUpperCase()));
            }
            return userEnteredEventInterestsArr;
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }
    }

    public static String returnUser(User user) {
        return user != null ? user.toString() : "User not found" ;
    }

    public static String returnEvent(Event event) {
        return event != null ? event.toString() : "Event not found" ;
    }
    public static String getEventTitleFromPayload(HashMap<String,Object> payload) {
        try {
            return (String) payload.get("title");
        } catch (Exception e) {
            return null;
        }
    }

    public static EventTypes getEventTypesFromPayload(HashMap<String,Object> payload) {
        try {
            String eventType = ((String) payload.get("eventType"));

            return EventTypes.valueOf(eventType.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
