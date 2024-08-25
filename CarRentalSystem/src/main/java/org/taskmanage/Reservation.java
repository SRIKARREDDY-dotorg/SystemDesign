package org.taskmanage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private final String reservationId;
    private final double totalPrice;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Car car;
    private final Customer customer;

    public Reservation(String reservationId, LocalDate startDate, LocalDate endDate, Car car, Customer customer) {
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.car = car;
        this.customer = customer;
        this.totalPrice = calculateTotalPrice(startDate, endDate, car.getRentalPricePerDay());
    }
    private double calculateTotalPrice(LocalDate startDate, LocalDate endDate, double rentalPricePerDay) {
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return days * rentalPricePerDay;
    }
    public String getReservationId() {
        return reservationId;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public Car getCar() {
        return car;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
}
