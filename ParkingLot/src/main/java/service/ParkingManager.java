package service;

import model.Vehicle;
import ticket.Ticket;

public class ParkingManager {
    private final ExitService exit;
    private final EntryService entry;

    public ParkingManager(ExitService exit, EntryService entry) {
        this.exit = exit;
        this.entry = entry;
    }

    public Ticket handleEntry(Vehicle v) throws InterruptedException {
        return entry.enter(v);
    }
    public boolean handleExitByTicketId(String ticketId) {
        return exit.exitByTicket(ticketId);
    }
    public boolean handleExitByPlate(String plate) {
        return exit.exitByPlate(plate);
    }
}
