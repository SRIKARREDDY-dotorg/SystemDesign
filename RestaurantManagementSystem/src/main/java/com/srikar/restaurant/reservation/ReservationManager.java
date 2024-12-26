package com.srikar.restaurant.reservation;

public class ReservationManager {
    private static ReservationManager instance;

    private ReservationManager() {
    }

    public static ReservationManager getInstance() {
        if (instance == null) {
            instance = new ReservationManager();
        }
        return instance;
    }
    // add user related params
    public Reservation makeReservation(Long partySize) {
        Reservation reservation = new Reservation();
        reservation.setPartySize(partySize);
        reservation.setStatus(ReservationStatus.PENDING);
        // add payment manager to adjust the status
        return reservation;
    }
    // add user related params
    public void cancelReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELLED);
    }
}
