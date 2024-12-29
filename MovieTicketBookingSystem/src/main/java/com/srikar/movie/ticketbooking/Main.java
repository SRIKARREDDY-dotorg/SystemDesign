package com.srikar.movie.ticketbooking;

import com.srikar.movie.ticketbooking.booking.Booking;
import com.srikar.movie.ticketbooking.seat.Seat;
import com.srikar.movie.ticketbooking.seat.SeatStatus;
import com.srikar.movie.ticketbooking.seat.SeatType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MovieTicketBookingSystem bookingSystem = MovieTicketBookingSystem.getInstance();

        Movie movie1 = new Movie("M1", "Movie 1", "Description 1", 120);
        Movie movie2 = new Movie("M2", "Movie 2", "Description 2", 90);
        bookingSystem.addMovie(movie1);
        bookingSystem.addMovie(movie2);
        Theater theater1 = new Theater("T1", "Theater 1", "Location 1", new ArrayList<>());
        Theater theater2 = new Theater("T2", "Theater 2", "Location 2", new ArrayList<>());
        bookingSystem.addTheater(theater1);
        bookingSystem.addTheater(theater2);
        Show show1 = new Show("S1", movie1, theater1, LocalDateTime.now(), LocalDateTime.now().plusMinutes(movie1.getDurationInMinutes()), createSeats(10,10));
        Show show2 = new Show("S2", movie2, theater2, LocalDateTime.now(), LocalDateTime.now().plusMinutes(movie2.getDurationInMinutes()), createSeats(8, 8));
        bookingSystem.addShow(show1);
        bookingSystem.addShow(show2);

        // Booking tickets
        User user1 = new User("U1", "John Doe", "johndoe@example.com");
        List<Seat> selectedSeats = Arrays.asList(show1.getSeats().get("1-5"), show2.getSeats().get("1-3"));
        Booking booking = bookingSystem.bookTickets(user1, show1, selectedSeats);
        if(booking != null) {
            System.out.println("Booking Successful, Booking ID:" + booking.getId());
            bookingSystem.confirmBooking(booking.getId());
        } else {
            System.out.println("Booking failed. Seats not available");
        }

        // Canceling a booking
        bookingSystem.cancelBooking(booking.getId());
        System.out.println("Booking Cancelled, Booking ID:" + booking.getId());
    }

    private static Map<String, Seat> createSeats(int rows, int columns) {
        Map<String, Seat> seats = new java.util.HashMap<>();
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                String seatId = row + "-" + column;
                SeatType seatType = (row <= 2) ? SeatType.NORMAL : SeatType.PREMIUM;
                double price = (seatType == SeatType.NORMAL) ? 100.0 : 150.0;
                seats.put(seatId, new Seat(seatId, row, column, seatType, price, SeatStatus.AVAILABLE));
            }
        }
        return seats;
    }
}