package pricing;

import ticket.Ticket;

public class LostTicketRule implements PricingRule {
    private final double penalty;

    public LostTicketRule(double penalty) {
        this.penalty = penalty;
    }
    @Override
    public boolean isApplicable(Ticket ticket, Long start, Long end) {
        return ticket == null;
    }

    @Override
    public double compute(Ticket ticket, Long start, Long end) {
        return penalty;
    }
}
