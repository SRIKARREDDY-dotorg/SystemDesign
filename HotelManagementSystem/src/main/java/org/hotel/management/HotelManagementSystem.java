package org.hotel.management;

import org.hotel.management.payment.Payment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HotelManagementSystem {
    private static HotelManagementSystem instance;
    private final Map<String, Room> rooms;
    private final Map<String, Guest> guests;
    private final Map<String, Reservation> reservations;

    private HotelManagementSystem() {
        // Private constructor to enforce singleton pattern
        rooms = new ConcurrentHashMap<>();
        guests = new ConcurrentHashMap<>();
        reservations = new ConcurrentHashMap<>();
    }

    public static synchronized HotelManagementSystem getInstance() {
        if (instance == null) {
            instance = new HotelManagementSystem();
        }
        return instance;
    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    public Guest getGuest(String guestId) {
        return guests.get(guestId);
    }
    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }
    public void addGuest(Guest guest) {
        guests.put(guest.getId(), guest);
    }

    public synchronized Reservation bookRoom(Guest guest, Room room, LocalDate startDate, LocalDate endDate) {
        if(room.getStatus() == RoomStatus.AVAILABLE) {
            room.book();
            String reservationId = generateReservationId();
            Reservation reservation = new Reservation(reservationId, guest, room, startDate, endDate);
            reservations.put(reservationId, reservation);
            return reservation;
        } else {
            throw new IllegalStateException("Room is not available for the given dates.");
        }
    }

    public synchronized void cancelReservation(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if(reservation != null) {
            reservation.cancel();
            reservations.remove(reservationId);
            System.out.println("Reservation cancelled: " + reservationId);
        } else {
            System.out.println("There is no reservation present with: "+ reservationId);
        }
    }

    public synchronized void checkIn(String reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if(reservation != null && reservation.getStatus() == ReservationStatus.CONFIRMED) {
            reservation.getRoom().checkIn();
        } else {
            throw new IllegalStateException("Reservation is not confirmed or does not exist.");
        }
    }

    public synchronized void checkOut(String reservationId, Payment payment) {
        Reservation reservation = reservations.get(reservationId);
        if(reservation != null && reservation.getStatus() == ReservationStatus.CONFIRMED) {
            Room room = reservation.getRoom();
            double amount = room.getPrice() * ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
            if(payment.processPayment(amount)) {
                room.checkOut();
                reservations.remove(reservationId);
            } else {
                throw new IllegalStateException("Payment failed.");
            }
        } else {
            throw new IllegalStateException("Reservation is not confirmed or does not exist.");
        }
    }
    private String generateReservationId() {
        // Generate a unique reservation ID
        return "RES" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
