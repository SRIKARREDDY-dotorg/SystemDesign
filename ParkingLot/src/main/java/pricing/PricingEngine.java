package pricing;

import ticket.Ticket;

import java.util.List;

public class PricingEngine {
    private final List<PricingRule> rules;

    public PricingEngine(List<PricingRule> rules) {
        this.rules = rules;
    }

    public double calculatePrice(Ticket ticket) {
        long start = ticket.getEntryTimeMs();
        long end = System.currentTimeMillis();
        double total = 0.0;
        for(PricingRule rule : rules) {
            if(rule.isApplicable(ticket, start, end)) {
                total += rule.compute(ticket, start, end);
            }
        }
        return total;
    }
}
