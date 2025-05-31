package src.model;

import java.util.UUID;

public class Vehicle {
    private final String id;
    private final String registrationNumber;
    private final String model;
    private final int capacity;

    public Vehicle(String registrationNumber, String model, int capacity) {
        this.id = UUID.randomUUID().toString();
        this.capacity = capacity;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public String getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSeats() {
        return capacity - 1; // excluding the driver.
    }
    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNumber='" + registrationNumber + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
