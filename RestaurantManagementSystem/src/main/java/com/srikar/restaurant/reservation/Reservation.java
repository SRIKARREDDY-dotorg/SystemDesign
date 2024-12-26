package com.srikar.restaurant.reservation;

public class Reservation {
    private final String id;
    private Long partySize;
    private ReservationStatus status;

    public Reservation() {
        this.id = generateId();
    }

    private String generateId() {
        // Generate a unique ID for the reservation
        return "RES-" + System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }
    public Long getPartySize() {
        return partySize;
    }
    public void setPartySize(Long partySize) {
        this.partySize = partySize;
    }
    public ReservationStatus getStatus() {
        return status;
    }
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
