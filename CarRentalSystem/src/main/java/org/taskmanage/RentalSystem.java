package org.taskmanage;

import org.taskmanage.payment.PayPalPaymentProcessor;
import org.taskmanage.payment.PaymentProcessor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RentalSystem {
    private static RentalSystem rentalSystem;
    private final Map<String, Car> cars;
    private final Map<String, Reservation> reservations;
    private final PaymentProcessor paymentProcessor;

    private RentalSystem() {
        this.cars = new HashMap<>();
        this.reservations =  new HashMap<>();
        paymentProcessor = new PayPalPaymentProcessor();
    }

    public synchronized static RentalSystem getInstance() {
        if (rentalSystem == null) {
            rentalSystem = new RentalSystem();
            return rentalSystem;
        }
        return rentalSystem;
    }
    public void addCar(Car car) {
        cars.put(car.getLicensePlate(), car);
    }
    public void removeCar(String licensePlate) {
        cars.remove(licensePlate);
    }
    public List<Car> searchCars(String make, String model, LocalDate startDate, LocalDate endDate) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars.values()) {
            if (car.getMake().equalsIgnoreCase(make) &&
                    car.getModel().equalsIgnoreCase(model) &&
                    isCarAvailable(car, startDate, endDate)) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }
    private boolean isCarAvailable(Car car, LocalDate startDate, LocalDate endDate) {
        for(Reservation reservation: reservations.values()) {
            if(reservation.getCar().equals(car)){
                if(startDate.isBefore(reservation.getEndDate()) && endDate.isAfter(reservation.getStartDate())) {
                    return false;
                }
            }
        }
        return true;
    }
    public synchronized Reservation makeReservation(Car car, Customer customer, LocalDate startDate, LocalDate endDate) {
        if(isCarAvailable(car, startDate, endDate)) {
            String reservationId = generateReservationId();
            Reservation reservation = new Reservation(reservationId, startDate, endDate, car, customer);
            reservations.put(reservationId, reservation);
            car.setAvailable(false);
            return reservation;
        }
        return null;
    }

    public synchronized void cancelReservation(String reservationId) {
        Reservation reservation = reservations.remove(reservationId);
        if(reservation != null) {
            reservation.getCar().setAvailable(true);
        }
    }

    public boolean processPayment(Reservation reservation) {
        return paymentProcessor.processPayment(reservation.getTotalPrice());
    }

    private String generateReservationId() {
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
