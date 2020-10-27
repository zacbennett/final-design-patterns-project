package com.example.springboot.classes;

import com.example.springboot.enums.Addresses;
import com.example.springboot.enums.EventTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnalyticsManager {
    private final Db db;

    public AnalyticsManager(Db db) {
        this.db = db;
    }

    public int getInterestedUsersByCity(Addresses address, EventTypes eventType) {
        return this.db.getInstance().getInterestedUsersByAddress(address,eventType).size();
    }
}
