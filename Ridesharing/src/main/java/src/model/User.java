package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final String id;
    private final String name;
    private final List<Vehicle> vehicles;
    private final List<Ride> offeredRides;
    private final List<Ride> takenRides;
    private double fuelSaved;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.vehicles = new ArrayList<>();
        this.offeredRides = new ArrayList<>();
        this.takenRides = new ArrayList<>();
        this.fuelSaved = 0.0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Ride> getOfferedRides() {
        return new ArrayList<>(offeredRides);
    }

    public void addOfferedRide(Ride ride) {
        offeredRides.add(ride);
    }

    public List<Ride> getTakenRides() {
        return new ArrayList<>(takenRides);
    }

    public void addTakenRide(Ride ride) {
        takenRides.add(ride);
        // add time from vehicles
        fuelSaved += ride.getRideTimeInHours();
    }

    public int getTotalOfferedRides() {
        return offeredRides.size();
    }
    public int getTotalTakenRides() {
        return takenRides.size();
    }
    public double getFuelSaved() {
        return fuelSaved;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}