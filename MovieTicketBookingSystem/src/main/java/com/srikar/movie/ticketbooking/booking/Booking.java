package com.srikar.movie.ticketbooking.booking;

import com.srikar.movie.ticketbooking.Show;
import com.srikar.movie.ticketbooking.User;
import com.srikar.movie.ticketbooking.seat.Seat;

import java.util.List;

public class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<Seat> seats;
    private final double price;
    private BookingStatus status;
    public Booking(String id, User user, Show show, List<Seat> seats, double price, BookingStatus status) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.price = price;
        this.status = status;
    }
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public Show getShow() {
        return show;
    }
    public List<Seat> getSeats() {
        return seats;
    }
    public double getPrice() {
        return price;
    }
    public BookingStatus getStatus() {
        return status;
    }
}
