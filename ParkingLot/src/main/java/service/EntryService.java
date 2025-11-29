package service;

import model.Vehicle;
import parking.ParkingLot;
import ticket.Ticket;

public class EntryService {
    private final ParkingLot lot;
    public EntryService(ParkingLot lot) { this.lot = lot; }

    public Ticket enter(Vehicle v) throws InterruptedException {
        return lot.checkIn(v);
    }
}
