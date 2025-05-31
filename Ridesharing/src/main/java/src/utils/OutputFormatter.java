package src.utils;

import src.model.Ride;
import src.model.User;
import src.model.Vehicle;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class for formatting output.
 */
public class OutputFormatter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Formats a user for display.
     * @param user The user to format
     * @return The formatted user string
     */
    public static String formatUser(User user) {
        if (user == null) {
            return "User not found";
        }

        return String.format("User: %s (ID: %s)", user.getName(), user.getId());
    }

    /**
     * Formats a vehicle for display.
     * @param vehicle The vehicle to format
     * @return The formatted vehicle string
     */
    public static String formatVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return "Vehicle not found";
        }

        return String.format("Vehicle: %s (Reg: %s, Capacity: %d, Available Seats: %d)",
                vehicle.getModel(), vehicle.getRegistrationNumber(), vehicle.getCapacity(), vehicle.getAvailableSeats());
    }

    /**
     * Formats a ride for display.
     * @param ride The ride to format
     * @return The formatted ride string
     */
    public static String formatRide(Ride ride) {
        if (ride == null) {
            return "Ride not found";
        }

        return String.format("Ride: %s to %s\n" +
                        "  Driver: %s\n" +
                        "  Vehicle: %s (%s)\n" +
                        "  Start Time: %s\n" +
                        "  End Time: %s\n" +
                        "  Ride Time: %.2f hours\n" +
                        "  Available Seats: %d/%d",
                ride.getOrigin(), ride.getDestination(),
                ride.getDriver().getName(),
                ride.getVehicle().getModel(), ride.getVehicle().getRegistrationNumber(),
                ride.getStartTime().format(DATE_TIME_FORMATTER),
                ride.getEndTime().format(DATE_TIME_FORMATTER),
                ride.getRideTimeInHours(),
                ride.getAvailableSeats(), ride.getTotalAvailableSeats());
    }

    /**
     * Formats a list of rides for display.
     * @param rides The rides to format
     * @return The formatted rides string
     */
    public static String formatRides(List<Ride> rides) {
        if (rides == null || rides.isEmpty()) {
            return "No rides found";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Found %d rides:\n", rides.size()));

        for (int i = 0; i < rides.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, formatRide(rides.get(i))));
            if (i < rides.size() - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Formats statistics for a user.
     * @param user The user
     * @param ridesOffered The number of rides offered
     * @param ridesTaken The number of rides taken
     * @param fuelSaved The amount of fuel saved
     * @return The formatted statistics string
     */
    public static String formatStatistics(User user, int ridesOffered, int ridesTaken, double fuelSaved) {
        if (user == null) {
            return "User not found";
        }

        return String.format("""
                        Statistics for %s:
                          Rides Offered: %d
                          Rides Taken: %d
                          Fuel Saved: %.2f units""",
                user.getName(), ridesOffered, ridesTaken, fuelSaved);
    }
}
