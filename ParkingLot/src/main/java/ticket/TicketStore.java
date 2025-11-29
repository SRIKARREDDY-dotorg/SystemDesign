package ticket;

import java.util.concurrent.ConcurrentHashMap;

public class TicketStore {
    private final ConcurrentHashMap<String, Ticket> byTicket = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Ticket> byPlate = new ConcurrentHashMap<>();

    public void save(final Ticket t) {
        byTicket.put(t.getTicketId(), t);
        byPlate.put(t.getPlate(), t);
    }

    public Ticket getByTicket(final String ticketId) {
        return byTicket.get(ticketId);
    }
    public Ticket getByPlate(final String plate) {
        return byPlate.get(plate);
    }
    public void remove(final Ticket t) {
        if (t == null) {
            return;
        }
        byTicket.remove(t.getTicketId());
        byPlate.remove(t.getPlate());
    }
}
