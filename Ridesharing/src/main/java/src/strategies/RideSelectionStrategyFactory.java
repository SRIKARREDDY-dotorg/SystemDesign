package src.strategies;

/**
 * Factory for creating ride selection strategies.
 */
public class RideSelectionStrategyFactory {

    public static final String FASTEST_RIDE = "fastest";
    public static final String EARLIEST_ARRIVAL = "earliest";

    /**
     * Creates a ride selection strategy based on the strategy name.
     * @param strategyName The name of the strategy to create
     * @return The created strategy, or null if the strategy name is invalid
     */
    public static RideSelectionStrategy createStrategy(String strategyName) {
        if (strategyName == null) {
            return null;
        }

        switch (strategyName.toLowerCase()) {
            case FASTEST_RIDE:
                return new FastestRideStrategy();
            case EARLIEST_ARRIVAL:
                return new EarliestArrivalStrategy();
            default:
                return null;
        }
    }
}
