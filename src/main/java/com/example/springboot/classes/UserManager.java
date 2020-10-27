package com.example.springboot.classes;

import com.example.springboot.enums.Addresses;
import com.example.springboot.enums.EventTypes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {
    private final NotificationManager notificationManager;
    private Db db;

    public UserManager(Db db, NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
        this.db = db;
    }

    public HashMap<UUID, User> getAllUsers() {
        return this.db.getInstance().getAllUsers();
    }

    public User addUser(String name, Addresses addressEnum, ArrayList<EventTypes> userEnteredEventInterestsArr) {

        User newUser = this.db.getInstance().addUser(name, addressEnum, userEnteredEventInterestsArr);
        this.notificationManager.handleNewUpdatedUser(newUser);
        return newUser;
    }

    public User getUserById(UUID id) {
        return this.db.getInstance().getUserById(id);
    }

    public User updateUserById(UUID id, String name, Addresses addressEnum, ArrayList<EventTypes> userEnteredEventInterestsArr) {
        User oldUserDetails =  this.db.getInstance().getUserById(id);
        if(oldUserDetails == null) return null;

        ArrayList<EventTypes> prevEventTypes = oldUserDetails.getEventType();
        Addresses prevAddress = oldUserDetails.getAddress();
        Boolean eventOrAddressChanged = (userEnteredEventInterestsArr != null && prevEventTypes.size() != userEnteredEventInterestsArr.size()) || (addressEnum != null && prevAddress !=addressEnum);

        User updatedUser = this.db.getInstance().updateUserById(id, name, addressEnum, userEnteredEventInterestsArr);

        if(eventOrAddressChanged) {
            this.notificationManager.handleNewUpdatedUser(updatedUser);
        }
        return updatedUser;
    }

    public String deleteUserById(UUID uuid) {
        return this.db.getInstance().deleteUserById(uuid);
    }
}
