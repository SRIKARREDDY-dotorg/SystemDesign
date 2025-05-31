package src.strategies;

import src.model.Ride;

import java.util.Comparator;
import java.util.List;

/**
 * Strategy to select the ride that reaches the destination earliest.
 */
public class EarliestArrivalStrategy implements RideSelectionStrategy {
    @Override
    public Ride selectRide(List<Ride> availableRides) {
        if (availableRides == null || availableRides.isEmpty()) {
            return null;
        }

        return availableRides.stream()
                .min(Comparator.comparing(Ride::getEndTime))
                .orElse(null);
    }
}
