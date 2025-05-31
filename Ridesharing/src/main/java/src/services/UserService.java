package src.services;

import src.model.User;
import src.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private final Map<String, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }
    public User createUser(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cant be null or empty");
        }

        User user = new User(name);
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Gets a user by ID.
     * @param userId The ID of the user
     * @return The user, or null if not found
     */
    public User getUser(String userId) {
        return users.get(userId);
    }

    /**
     * Gets all users.
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Adds a vehicle to a user.
     * @param userId The ID of the user
     * @param registrationNumber The registration number of the vehicle
     * @param model The model of the vehicle
     * @param capacity The capacity of the vehicle (including driver)
     * @return The created vehicle, or null if the user is not found
     */
    public Vehicle addVehicle(String userId, String registrationNumber, String model, int capacity) {
        User user = getUser(userId);
        if (user == null) {
            return null;
        }

        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be null or empty");
        }

        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }

        if (capacity < 2) {
            throw new IllegalArgumentException("Capacity must be at least 2 (driver + at least 1 passenger)");
        }

        Vehicle vehicle = new Vehicle(registrationNumber, model, capacity);
        user.addVehicle(vehicle);
        return vehicle;
    }

    /**
     * Gets all vehicles for a user.
     * @param userId The ID of the user
     * @return List of all vehicles for the user, or empty list if user not found
     */
    public List<Vehicle> getUserVehicles(String userId) {
        User user = getUser(userId);
        if (user == null) {
            return new ArrayList<>();
        }

        return user.getVehicles();
    }
}
