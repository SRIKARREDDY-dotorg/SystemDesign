package src.strategies;

import src.model.Ride;

import java.util.List;

/**
 * Interface for ride selection strategies.
 * Different implementations can provide different ways to select a ride.
 */
public interface RideSelectionStrategy {
    /**
     * Selects the best ride from a list of available rides based on the strategy.
     * @param availableRides List of available rides
     * @return The selected ride, or null if no rides are available
     */
    Ride selectRide(List<Ride> availableRides);
}
