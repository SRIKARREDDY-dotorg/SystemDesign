package pricing;

import ticket.Ticket;

public class BaseHourlyPricingRule implements PricingRule {
    private final double hourlyRate;

    public BaseHourlyPricingRule(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public boolean isApplicable(Ticket ticket, Long start, Long end) {
        return true;
    }

    @Override
    public double compute(Ticket ticket, Long start, Long end) {
        long duration = end - start;
        return hourlyRate * duration / 3600000.0;
    }
}
