package service;

import parking.ParkingLot;
import pricing.PricingEngine;
import pricing.transaction.TransactionManager;

public class ExitService {
    private final ParkingLot lot;
    private final TransactionManager txMgr;
    private final PricingEngine pEngine;
    public ExitService(ParkingLot lot, TransactionManager txMgr, PricingEngine pEngine) {
        this.lot = lot; this.txMgr = txMgr; this.pEngine = pEngine;
    }

    public boolean exitByTicket(String ticketId) {
        return lot.checkoutByTicketId(ticketId, txMgr, pEngine);
    }
    public boolean exitByPlate(String plate) {
        return lot.checkoutByPlate(plate, txMgr, pEngine);
    }
}
