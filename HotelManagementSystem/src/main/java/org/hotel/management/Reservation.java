package org.hotel.management;

import java.time.LocalDate;

public class Reservation {
    private final String id;
    private final Guest guest;
    private final Room room;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private ReservationStatus status;

    public Reservation(String id, Guest guest, Room room, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.guest = guest;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ReservationStatus.CONFIRMED;
    }

    public synchronized void cancel() {
        if(status == ReservationStatus.CONFIRMED) {
            status = ReservationStatus.CANCELLED;
            room.checkOut();
        } else {
            throw new IllegalStateException("Reservation is not confirmed.");
        }
    }

    // getters
    public String getId() {
        return id;
    }
    public Guest getGuest() {
        return guest;
    }
    public Room getRoom() {
        return room;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public ReservationStatus getStatus() {
        return status;
    }
}
