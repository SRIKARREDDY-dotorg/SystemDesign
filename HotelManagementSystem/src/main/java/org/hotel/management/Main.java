package org.hotel.management;

import org.hotel.management.payment.CreditCardPayment;
import org.hotel.management.payment.Payment;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        HotelManagementSystem hotelManagementSystem = HotelManagementSystem.getInstance();

        // create guests
        Guest guest1 = new Guest("G001","John Doe", "123 Main St", "555-1234");
        Guest guest2 = new Guest("G002", "Jane Smith", "456 Oak Ave", "555-5678");
        hotelManagementSystem.addGuest(guest1);
        hotelManagementSystem.addGuest(guest2);

        // Create rooms
        Room room1 = new Room("R001", 100.0, RoomType.SINGLE);
        Room room2 = new Room("R002", 150.0, RoomType.DOUBLE);
        hotelManagementSystem.addRoom(room1);
        hotelManagementSystem.addRoom(room2);

        // Book a room
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        Reservation reservation1 = hotelManagementSystem.bookRoom(guest1, room1, startDate, endDate);
        if(reservation1 != null) {
            System.out.println("Reservation 1: " + reservation1);
        } else {
            System.out.println("Room not available for the given dates.");
        }

        // check in
        assert reservation1 != null;
        hotelManagementSystem.checkIn(reservation1.getId());
        System.out.println("Checked In" + reservation1.getId());

        // check out
        Payment payment = new CreditCardPayment();
        hotelManagementSystem.checkOut(reservation1.getId(), payment);
        System.out.println("Checked Out" + reservation1.getId());

        // cancel reservation
        hotelManagementSystem.cancelReservation(reservation1.getId());
        System.out.println("Reservation cancelled: " + reservation1.getId());
    }
}