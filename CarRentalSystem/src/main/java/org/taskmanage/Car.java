package org.taskmanage;

public class Car {
    private final String model;
    private final String make;
    private final String licensePlate;
    private final int year;
    private final double rentalPricePerDay;
    private boolean available;
    public Car(String model, String make, String licensePlate, int year, double rentalPricePerDay) {
        this.model = model;
        this.make = make;
        this.licensePlate = licensePlate;
        this.year = year;
        this.rentalPricePerDay = rentalPricePerDay;
        this.available = true;
    }
    public String getModel() {
        return model;
    }
    public String getMake() {
        return make;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
}
