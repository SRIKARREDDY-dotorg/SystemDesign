package src.strategies;

import src.model.Ride;

import java.util.Comparator;
import java.util.List;

/**
 * Strategy to select the ride with the shortest ride time.
 */
public class FastestRideStrategy implements RideSelectionStrategy {
    @Override
    public Ride selectRide(List<Ride> availableRides) {
        if (availableRides == null || availableRides.isEmpty()) {
            return null;
        }

        return availableRides.stream()
                .min(Comparator.comparingDouble(Ride::getRideTimeInHours))
                .orElse(null);
    }
}