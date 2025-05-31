package src.services;

import src.model.Ride;
import src.model.User;
import src.model.Vehicle;
import src.strategies.RideSelectionStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RideService {
    private final Map<String, Ride> rides;
    private final UserService userService;

    public RideService(UserService userService) {
        this.rides = new HashMap<>();
        this.userService = userService;
    }

    /**
     * Offers a ride.
     * @param driverId The ID of the driver
     * @param vehicleId The ID of the vehicle
     * @param origin The origin of the ride
     * @param destination The destination of the ride
     * @param startTime The start time of the ride
     * @param rideTimeInHours The ride time in hours
     * @param availableSeats The number of available seats
     * @return The created ride, or null if the driver or vehicle is not found
     */
    public Ride offerRide(String driverId, String vehicleId, String origin, String destination,
                          LocalDateTime startTime, double rideTimeInHours, int availableSeats) {
        User driver = userService.getUser(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver not found");
        }

        // Find the vehicle in the driver's vehicles
        Vehicle vehicle = driver.getVehicles().stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst()
                .orElse(null);

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found or does not belong to the driver");
        }

        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be null or empty");
        }

        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be null or empty");
        }

        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }

        if (rideTimeInHours <= 0) {
            throw new IllegalArgumentException("Ride time must be positive");
        }

        if (availableSeats <= 0 || availableSeats > vehicle.getAvailableSeats()) {
            throw new IllegalArgumentException("Available seats must be positive and not exceed vehicle capacity");
        }

        Ride ride = new Ride(driver, vehicle, origin, destination, startTime, rideTimeInHours, availableSeats);
        rides.put(ride.getId(), ride);
        driver.addOfferedRide(ride);

        return ride;
    }

    /**
     * Gets a ride by ID.
     * @param rideId The ID of the ride
     * @return The ride, or null if not found
     */
    public Ride getRide(String rideId) {
        return rides.get(rideId);
    }

    /**
     * Gets all rides.
     * @return List of all rides
     */
    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }

    /**
     * Gets all available rides for a given origin and destination.
     * @param origin The origin of the ride
     * @param destination The destination of the ride
     * @return List of available rides
     */
    public List<Ride> getAvailableRides(String origin, String destination) {
        return rides.values().stream()
                .filter(ride -> ride.matches(origin, destination) && ride.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Selects a ride for a passenger.
     * @param passengerId The ID of the passenger
     * @param origin The origin of the ride
     * @param destination The destination of the ride
     * @param selectionStrategy The strategy to use for selecting a ride
     * @return The selected ride, or null if no suitable ride is found
     */
    public Ride selectRide(String passengerId, String origin, String destination, RideSelectionStrategy selectionStrategy) {
        // two users
        // different src to destination
        User passenger = userService.getUser(passengerId);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger not found");
        }

        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be null or empty");
        }

        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination cannot be null or empty");
        }


        if (selectionStrategy == null) {
            throw new IllegalArgumentException("Selection strategy cannot be null");
        }

        List<Ride> availableRides = getAvailableRides(origin, destination);
        Ride selectedRide = selectionStrategy.selectRide(availableRides);

        if (selectedRide != null) {
            boolean added = selectedRide.addPassenger(passenger);
            if (added) {
                passenger.addTakenRide(selectedRide);
                return selectedRide;
            }
        }

        return null;
    }
}
