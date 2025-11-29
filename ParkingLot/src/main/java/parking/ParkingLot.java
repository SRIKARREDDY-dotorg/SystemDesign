package parking;

import allocation.SpotAllocator;
import model.Vehicle;
import pricing.PricingEngine;
import pricing.transaction.TransactionManager;
import ticket.Ticket;
import ticket.TicketStore;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ParkingLot {
    private final List<ParkingFloor> floors;
    private final SpotAllocator spotAllocator;
    private final TicketStore ticketStore;
    private final Object monitor = new Object();
    private final AtomicLong ticketSeq = new AtomicLong(1);

    public ParkingLot(List<ParkingFloor> floors, SpotAllocator spotAllocator) {
        this.floors = floors;
        this.spotAllocator = spotAllocator;
        this.ticketStore = new TicketStore();
    }

    public Ticket checkIn(Vehicle v) throws InterruptedException {
        while(true) {
            ParkingSpot spot = spotAllocator.findSpot(v);
            if(spot != null) {
                synchronized (spot) {
                    if(spot.isFree()) {
                        boolean ok = spot.assignVehicle(v);
                        if(!ok) continue;
                        String ticketId = "T" + ticketSeq.getAndIncrement();
                        Ticket ticket = new Ticket(ticketId, v.getPlate(), spot);
                        ticketStore.save(ticket);
                        return ticket;
                    } else {
                        continue;
                    }
                }
            }

            synchronized (monitor) {
                monitor.wait(1000);
            }
        }
    }

    public boolean checkoutByTicketId(String ticketId, TransactionManager txnMgr, PricingEngine pEngine) {
        Ticket t = ticketStore.getByTicket(ticketId);
        if(t == null) {
            return false;
        }
        return checkout(t, txnMgr, pEngine);
    }

    public boolean checkoutByPlate(String plate, TransactionManager txnMgr, PricingEngine pEngine) {
        Ticket t = ticketStore.getByPlate(plate);
        if(t == null) {
            return false;
        }
        return checkout(t, txnMgr, pEngine);
    }

    private boolean checkout(Ticket t, TransactionManager txnMgr, PricingEngine pEngine) {
        ParkingSpot spot = t.getSpot();
        synchronized (spot) {
            if(spot.isFree()) {
                // already released by another thread -> idempotent success
                ticketStore.remove(t);
                return false;
            }

            double amount = pEngine.calculatePrice(t);

            // payment with retry
            int attempt = 0;
            boolean paid = false;
            while(attempt < 3) {
                attempt++;
                if(txnMgr.initiatePayment(amount)) {
                    paid = true;
                    break;
                }
            }

            if (!paid) {
                // escalate: in production we'd mark transaction as pending and notify admin
                return false;
            }

            spot.releaseVehicle();
            ticketStore.remove(t);
        }

        synchronized (monitor) {
            monitor.notifyAll();
        }
        return true;
    }

    // convenience: used for demos or metrics
    public int availableSpotsCount() {
        int cnt = 0;
        for (ParkingFloor pf : floors) {
            for (ParkingSpot s : pf.getSpots()) if (s.isFree()) cnt++;
        }
        return cnt;
    }

    public TicketStore getTicketStore() { return ticketStore; }
}
