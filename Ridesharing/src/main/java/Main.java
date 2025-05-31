import src.model.Ride;
import src.model.User;
import src.model.Vehicle;
import src.services.RideService;
import src.services.StatisticsService;
import src.services.UserService;
import src.strategies.RideSelectionStrategy;
import src.strategies.RideSelectionStrategyFactory;
import src.utils.OutputFormatter;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        UserService userService = new UserService();
        RideService rideService = new RideService(userService);
        StatisticsService statisticsService = new StatisticsService(userService);

        // Create users
        User alice = userService.createUser("Alice");
        User bob = userService.createUser("Bob");
        User charlie = userService.createUser("Charlie");
        User dave = userService.createUser("Dave");

        System.out.println("Created users:");
        System.out.println(OutputFormatter.formatUser(alice));
        System.out.println(OutputFormatter.formatUser(bob));
        System.out.println(OutputFormatter.formatUser(charlie));
        System.out.println(OutputFormatter.formatUser(dave));
        System.out.println();

        // Add vehicles
        Vehicle aliceCar = userService.addVehicle(alice.getId(), "ABC123", "Toyota Camry", 5);
        Vehicle bobCar = userService.addVehicle(bob.getId(), "DEF456", "Honda Civic", 4);
        Vehicle charlieCar = userService.addVehicle(charlie.getId(), "GHI789", "Tesla Model 3", 5);

        System.out.println("Added vehicles:");
        System.out.println(OutputFormatter.formatVehicle(aliceCar));
        System.out.println(OutputFormatter.formatVehicle(bobCar));
        System.out.println(OutputFormatter.formatVehicle(charlieCar));
        System.out.println();

        // Offer rides
        LocalDateTime now = LocalDateTime.now();

        Ride aliceRide = rideService.offerRide(
                alice.getId(), aliceCar.getId(),
                "Home", "Office",
                now.plusHours(1), 2.0, 4);

        Ride bobRide = rideService.offerRide(
                bob.getId(), bobCar.getId(),
                "Home", "Office",
                now.plusMinutes(30), 2.5, 3);

        Ride charlieRide = rideService.offerRide(
                charlie.getId(), charlieCar.getId(),
                "Home", "Office",
                now.plusHours(2), 1.5, 4);

        System.out.println("Offered rides:");
        System.out.println(OutputFormatter.formatRide(aliceRide));
        System.out.println();
        System.out.println(OutputFormatter.formatRide(bobRide));
        System.out.println();
        System.out.println(OutputFormatter.formatRide(charlieRide));
        System.out.println();

        // Get available rides
        List<Ride> availableRides = rideService.getAvailableRides("Home", "Office");
        System.out.println("Available rides from Home to Office:");
        System.out.println(OutputFormatter.formatRides(availableRides));
        System.out.println();

        // Select rides using different strategies
        System.out.println("Dave selects a ride using fastest ride strategy:");
        RideSelectionStrategy fastestRideStrategy = RideSelectionStrategyFactory.createStrategy(RideSelectionStrategyFactory.FASTEST_RIDE);
        Ride selectedRide1 = rideService.selectRide(dave.getId(), "Home", "Office", fastestRideStrategy);
        System.out.println(OutputFormatter.formatRide(selectedRide1));
        System.out.println();

        // Create another user and select a ride using earliest arrival strategy
        User eve = userService.createUser("Eve");
        System.out.println("Created user: " + OutputFormatter.formatUser(eve));
        System.out.println();

        System.out.println("Eve selects a ride using earliest arrival strategy:");
        RideSelectionStrategy earliestArrivalStrategy = RideSelectionStrategyFactory.createStrategy(RideSelectionStrategyFactory.EARLIEST_ARRIVAL);
        Ride selectedRide2 = rideService.selectRide(eve.getId(), "Home", "Office", earliestArrivalStrategy);
        System.out.println(OutputFormatter.formatRide(selectedRide2));
        System.out.println();

        // Print statistics
        System.out.println("Statistics: ");
        System.out.println(OutputFormatter.formatStatistics(
                alice,
                statisticsService.getTotalRidesOffered(alice.getId()),
                statisticsService.getTotalRidesTaken(alice.getId()),
                statisticsService.getTotalFuelSaved(alice.getId())));
        System.out.println();

        System.out.println(OutputFormatter.formatStatistics(
                dave,
                statisticsService.getTotalRidesOffered(dave.getId()),
                statisticsService.getTotalRidesTaken(dave.getId()),
                statisticsService.getTotalFuelSaved(dave.getId())));
        System.out.println();

        System.out.println(OutputFormatter.formatStatistics(
                eve,
                statisticsService.getTotalRidesOffered(eve.getId()),
                statisticsService.getTotalRidesTaken(eve.getId()),
                statisticsService.getTotalFuelSaved(eve.getId())));
    }
}
