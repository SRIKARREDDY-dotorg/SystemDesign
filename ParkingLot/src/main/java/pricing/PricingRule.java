package pricing;

import ticket.Ticket;

public interface PricingRule {
    boolean isApplicable(Ticket ticket, Long start, Long end);
    double compute(Ticket ticket, Long start, Long end);
}
