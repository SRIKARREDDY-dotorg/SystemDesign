package ticket;

import parking.ParkingSpot;

public class Ticket {
    private final String ticketId;
    private final String plate;
    private final ParkingSpot spot;
    private final long entryTimeMs;


    public Ticket(String ticketId, String plate, ParkingSpot spot) {
        this.ticketId = ticketId;
        this.plate = plate;
        this.spot = spot;
        this.entryTimeMs = System.currentTimeMillis();
    }

    public String getPlate() {
        return plate;
    }
    public ParkingSpot getSpot() {
        return spot;
    }
    public long getEntryTimeMs() {
        return entryTimeMs;
    }
    public String getTicketId() {
        return ticketId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "plate='" + plate + '\'' +
                ", spot=" + spot.getId() +
                ", entryTimeMs=" + entryTimeMs +
                '}';
    }
}
