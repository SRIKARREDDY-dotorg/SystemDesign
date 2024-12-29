package com.srikar.movie.ticketbooking;

import com.srikar.movie.ticketbooking.booking.Booking;
import com.srikar.movie.ticketbooking.booking.BookingStatus;
import com.srikar.movie.ticketbooking.seat.Seat;
import com.srikar.movie.ticketbooking.seat.SeatStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MovieTicketBookingSystem {
    private static MovieTicketBookingSystem instance;
    private final List<Movie> movies;
    private final List<Theater> theaters;
    private final Map<String, Show> shows;
    private final Map<String, Booking> bookings;

    private static final String BOOKING_ID_PREFIX = "BKG";
    private static final AtomicLong bookingCounter = new AtomicLong(0);

    private MovieTicketBookingSystem() {
        movies = new ArrayList<>();
        theaters = new ArrayList<>();
        shows = new ConcurrentHashMap<>();
        bookings = new ConcurrentHashMap<>();
    }

    public static synchronized MovieTicketBookingSystem getInstance() {
        if (instance == null) {
            instance = new MovieTicketBookingSystem();
        }
        return instance;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }
    public void addTheater(Theater theater) {
        theaters.add(theater);
    }
    public void addShow(Show show) {
        shows.put(show.getId(), show);
    }
    public Show getShow(String showId) {
        return shows.get(showId);
    }
    public List<Movie> getMovies() {
        return movies;
    }
    public List<Theater> getTheaters() {
        return theaters;
    }
    public synchronized Booking bookTickets(User user, Show show, List<Seat> selectedSeats) {
        if(areSeatsAvailable(show, selectedSeats)) {
            markSeatsBooked(show, selectedSeats);
            double totalPrice = calculateTotalPrice(selectedSeats);
            String bookingId = generateBookingId();
            Booking booking = new Booking(bookingId, user, show, selectedSeats, totalPrice, BookingStatus.PENDING);
            bookings.put(bookingId, booking);
            return booking;
        }
        return null;
    }

    private String generateBookingId() {
        long bookingNumber = bookingCounter.incrementAndGet();
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return BOOKING_ID_PREFIX + timeStamp + String.format("%06d", bookingNumber);
    }

    private boolean areSeatsAvailable(Show show, List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            Seat showSeat = show.getSeats().get(seat.getId());
            if (showSeat == null || showSeat.getStatus() != SeatStatus.AVAILABLE) {
                return false;
            }
        }
        return true;
    }

    private void markSeatsBooked(Show show, List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            show.getSeats().get(seat.getId()).setStatus(SeatStatus.BOOKED);
        }
    }
    private double calculateTotalPrice(List<Seat> selectedSeats) {
        return selectedSeats.stream().mapToDouble(Seat::getPrice).sum();
    }
    public void cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.getStatus() == BookingStatus.PENDING) {
            booking.setStatus(BookingStatus.CANCELLED);
            markSeatsAvailable(booking.getShow(), booking.getSeats());
        }
    }
    private void markSeatsAvailable(Show show, List<Seat> seats) {
        for (Seat seat : seats) {
            show.getSeats().get(seat.getId()).setStatus(SeatStatus.AVAILABLE);
        }
    }
    public void confirmBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.getStatus() == BookingStatus.PENDING) {
            booking.setStatus(BookingStatus.CONFIRMED);
            // process payment and send confirmation
        }
    }
}
