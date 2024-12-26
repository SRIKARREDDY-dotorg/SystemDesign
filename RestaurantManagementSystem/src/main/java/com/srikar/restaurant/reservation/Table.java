package com.srikar.restaurant.reservation;

public class Table {
    private final Long size;
    private final String id;
    private Long frequency;
    private double cost;

    public Table(Long size, Long frequency, double cost) {
        this.size = size;
        this.id = generateId();
        this.frequency = frequency;
        this.cost = cost;
    }

    public Long getSize() {
        return size;
    }
    public Long getFrequency() {
        return frequency;
    }
    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }
    public String getId() {
        return id;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public String generateId() {
        // Generate a unique ID for the table
        return "TAB-" + System.currentTimeMillis();
    }

}
