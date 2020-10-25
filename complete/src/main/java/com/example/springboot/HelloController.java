package com.example.springboot;
import com.example.springboot.enums.Addresses;
import com.example.springboot.enums.EventTypes;
import com.example.springboot.helper.Helper;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.classes.*;

import java.util.*;

@RestController
public class HelloController {
	private final UserManager userManager;
	private final EventManager eventManager;
	private final AnalyticsManager analyticsManager;
	private final Db db;

	public HelloController() {
		this.db = new Db();
		NotificationManager notificationManager = new NotificationManager(this.db);
		this.userManager = new UserManager(this.db, notificationManager);
		this.eventManager = new EventManager(this.db, notificationManager);
		this.analyticsManager = new AnalyticsManager(this.db);
	}

	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable String id) {
		System.out.print(id);
		UUID uuid = UUID.fromString(id);
		User userToReturn = this.userManager.getUserById(uuid);
		return Helper.returnUser(userToReturn);
	}

	@GetMapping("/users")
	public HashMap<UUID, User> getUserById() {
		return this.userManager.getAllUsers();
	}

	// POST /user
	@PostMapping(value = "/users", consumes = "application/json")
	public String postUser(@RequestBody HashMap<String,Object> payload) {
		User user = null;

		String name = Helper.getUserNameFromPayload(payload);
		Addresses addressEnum = Helper.getAddressFromPayload(payload);
		ArrayList<EventTypes> userEnteredEventInterestsArr = Helper.getUserEventTypesFromPayload(payload);

		user = this.userManager.addUser(name, addressEnum, userEnteredEventInterestsArr);

		return user != null ? user.toString(): "Error creating user";
	}

	@PutMapping(value = "/users", consumes = "application/json")
	public String updateUserById(@RequestBody HashMap<String,Object> payload) {
		UUID id = Helper.getUserIDFromPayload(payload);

		String name = Helper.getUserNameFromPayload(payload);
		Addresses addressEnum = Helper.getAddressFromPayload(payload);
		ArrayList<EventTypes> userEnteredEventInterestsArr = Helper.getUserEventTypesFromPayload(payload);

		User updatedUser = this.userManager.updateUserById(id, name, addressEnum, userEnteredEventInterestsArr);
		return Helper.returnUser(updatedUser);
	}

	@DeleteMapping(value = "/users/{id}")
	public String deleteUserById(@PathVariable String id) {
		UUID uuid = UUID.fromString(id);
		return this.userManager.deleteUserById(uuid);
	}


	// Event routes
	@GetMapping("/events/{id}")
	public String getEventById(@PathVariable String id) {
		System.out.print(id);
		UUID uuid = UUID.fromString(id);
		Event eventToReturn = this.eventManager.getEventById(uuid);
		return Helper.returnEvent(eventToReturn);
	}

	@GetMapping("/events")
	public HashMap<UUID, Event> getEventById() {
		return this.eventManager.getAllEvents();
	}

	// POST /user
	@PostMapping(value = "/events", consumes = "application/json")
	public String createEvent(@RequestBody HashMap<String,Object> payload) {
		Event event = null;

		String title = Helper.getEventTitleFromPayload(payload);
		Addresses addressEnum = Helper.getAddressFromPayload(payload);
		EventTypes eventType = Helper.getEventTypesFromPayload(payload);

		event = this.eventManager.addEvent(title, addressEnum, eventType);

		return event != null ? event.toString(): "Error creating event";
	}

	@PutMapping(value = "/events", consumes = "application/json")
	public String updateEventById(@RequestBody HashMap<String,Object> payload) {
		UUID id = Helper.getUserIDFromPayload(payload);

		String title = Helper.getEventTitleFromPayload(payload);
		Addresses addressEnum = Helper.getAddressFromPayload(payload);
		EventTypes eventType = Helper.getEventTypesFromPayload(payload);

		Event updatedEvent = this.eventManager.updateEventById(id, title, addressEnum, eventType);
		return Helper.returnEvent(updatedEvent);
	}

	@DeleteMapping(value = "/events/{id}")
	public String deleteEventById(@PathVariable String id) {
		UUID uuid = UUID.fromString(id);
		return this.eventManager.deleteEventById(uuid);
	}

	@GetMapping("/analytics")
	public String getAnalytics(@RequestParam(name = "address") String address, @RequestParam(name = "eventType") String eventType) {
		Addresses addressEnum = Addresses.valueOf(address.toUpperCase());
		EventTypes eventTypeEnum = EventTypes.valueOf(eventType.toUpperCase());

		int interestedUsers = this.analyticsManager.getInterestedUsersByCity(addressEnum, eventTypeEnum);
		return "Number of interested users: " + interestedUsers;
	}
}
