package src.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ride {
    private final String id;
    private final User driver;
    private final Vehicle vehicle;
    private final String origin;
    private final String destination;
    private final LocalDateTime startTime;
    private final double rideTimeInHours;
    private final int availableSeats;
    private final List<User> passengers;

    public Ride(User driver, Vehicle vehicle, String origin, String destination, LocalDateTime startTime, double rideTimeInHours, int availableSeats) {
        this.id = UUID.randomUUID().toString();
        this.driver = driver;
        this.vehicle = vehicle;
        this.origin = origin;
        this.destination = destination;
        this.startTime = startTime;
        this.rideTimeInHours = rideTimeInHours;
        this.availableSeats = Math.min(availableSeats, vehicle.getAvailableSeats());
        this.passengers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public User getDriver() {
        return driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes((long) (rideTimeInHours * 60));
    }

    public double getRideTimeInHours() {
        return rideTimeInHours;
    }

    public int getAvailableSeats() {
        return availableSeats - passengers.size();
    }

    public int getTotalAvailableSeats(){
        return availableSeats;
    }

    public List<User> getPassengers() {
        return new ArrayList<>(passengers);
    }

    public boolean addPassenger(User passenger) {
        if(getAvailableSeats() > 0 && !passengers.contains(passenger) && passenger != driver) {
            passengers.add(passenger);
            return true;
        }
        return false;
    }

    public boolean matches(String origin, String destination) {
        return this.origin.equalsIgnoreCase(origin) && this.destination.equalsIgnoreCase(destination);
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", driver=" + driver.getName() +
                ", vehicle=" + vehicle.getModel() +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", startTime=" + startTime +
                ", rideTime=" + rideTimeInHours + " hours" +
                ", availableSeats=" + getAvailableSeats() +
                '}';
    }
}
