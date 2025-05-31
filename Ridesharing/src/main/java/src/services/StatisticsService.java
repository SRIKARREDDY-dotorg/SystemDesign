package src.services;

import src.model.User;

public class StatisticsService {
    private final UserService userService;

    public StatisticsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets the total number of rides offered by a user.
     * @param userId The ID of the user
     * @return The total number of rides offered, or 0 if the user is not found
     */
    public int getTotalRidesOffered(String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return 0;
        }

        return user.getTotalOfferedRides();
    }

    /**
     * Gets the total number of rides taken by a user.
     * @param userId The ID of the user
     * @return The total number of rides taken, or 0 if the user is not found
     */
    public int getTotalRidesTaken(String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return 0;
        }

        return user.getTotalTakenRides();
    }

    /**
     * Gets the total fuel saved by a user.
     * @param userId The ID of the user
     * @return The total fuel saved, or 0 if the user is not found
     */
    public double getTotalFuelSaved(String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return 0;
        }

        return user.getFuelSaved();
    }
}
