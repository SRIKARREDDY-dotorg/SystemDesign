package org.taskmanage;

import java.time.LocalDate;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CarRentalSystemDemo {
    public static void main(String[] args) {
        RentalSystem rentalSystem = RentalSystem.getInstance();

        // Add cars to the rental system
        rentalSystem.addCar(new Car("Toyota", "Camry", "ABC123",2022, 50.0));
        rentalSystem.addCar(new Car("Honda", "Civic",  "XYZ789",2021, 45.0));
        rentalSystem.addCar(new Car("Ford", "Mustang",  "DEF456",2023, 80.0));

        // Create customers
        Customer customer1 = new Customer("John Doe", "john@example.com", "DL1234");

        // Make reservations
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        List<Car> availableCars = rentalSystem.searchCars("Camry", "Toyota", startDate, endDate);

        if(!availableCars.isEmpty()) {
            Car selectedCar = availableCars.get(0);
            Reservation reservation = rentalSystem.makeReservation(selectedCar, customer1, startDate, endDate);
            if(reservation != null) {
                boolean paymentSuccess = rentalSystem.processPayment(reservation);
                if(paymentSuccess) {
                    System.out.println("Reservation confirmed: " + reservation.getReservationId());
                } else {
                    rentalSystem.cancelReservation(reservation.getReservationId());
                    System.out.println("Payment failed. Reservation canceled.");
                }
            } else {
                System.out.println("Selected car is not available for the given dates.");
            }
        } else {
            System.out.println("No cars available for the given criteria.");
        }
    }
}