/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/
import java.util.*;

enum RideStatus {
    REQUESTED,
    DRIVER_ASSIGNED,
    EN_ROUTE_TO_PICKUP,
    STARTED,
    COMPLETED,
    CANCELLED_BY_DRIVER,
    CANCELLED_BY_RIDER
}

enum AvailabilityStatus {
    AVAILABLE,
    UNAVAILABLE
}

class Location {
    int x;
    int y;
    
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int distanceTo(Location other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y-other.y);
    }
}

abstract class User {
    private final String id;
    private final String name;
    
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}

class Rider extends User {
    private List<Ride> rideHistory = new ArrayList<>();
    
    public Rider(String id, String name) {
        super(id, name);
    }
    
    public void addRideToHistory(Ride ride) {
        rideHistory.add(ride);
    }
    
    public List<Ride> getRideHistory() {
        return rideHistory;
    }
}

class Driver extends User {
    private AvailabilityStatus status = AvailabilityStatus.AVAILABLE;
    private Location currentLocation;
    private List<Ride> rideHistory = new ArrayList<>();
    
    public Driver(String id, String name, Location location) {
        super(id, name);
        this.currentLocation = location;
    }
    
    public void setAvailability(AvailabilityStatus status) {
        this.status = status;
    }
    
    public AvailabilityStatus getAvailability() {
        return status;
    }
    
    public Location getLocation() {
        return currentLocation;
    }
    
    public void updateLocation(Location location) {
        this.currentLocation = location;
    }
    
    public void addRideToHistory(Ride ride) {
        rideHistory.add(ride);
    }
    
    public List<Ride> getRideHistory() {
        return rideHistory;
    }
}

class Ride {
    private final UUID id;
    private final Rider rider;
    private final Driver driver;
    private final Location pickUp;
    private final Location dropOff;
    private RideStatus status;
    
    public Ride(Rider rider, Driver driver, Location pickUp, Location dropOff) {
        this.id = UUID.randomUUID();
        this.rider = rider;
        this.driver = driver;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.status = RideStatus.REQUESTED;
    }
    
    public void startRide() {
        if(status == RideStatus.EN_ROUTE_TO_PICKUP || status == RideStatus.DRIVER_ASSIGNED) {
            status = RideStatus.STARTED;
        }
    }
    
    public void completeRide() {
        if(status == RideStatus.STARTED) {
            status = RideStatus.COMPLETED;
        }
    }
    
    public void cancelByRider() {
        if(status == RideStatus.REQUESTED || status == RideStatus.DRIVER_ASSIGNED) {
            status = RideStatus.CANCELLED_BY_RIDER;
        }
    }
    
    public void cancelByDriver() {
        if(status == RideStatus.REQUESTED || status == RideStatus.DRIVER_ASSIGNED) {
            status = RideStatus.CANCELLED_BY_DRIVER;
        }
    }
    
    public RideStatus getStatus() {
        return status;
    }
    
    public Driver getDriver() {
        return driver;
    }
    
    public Rider getRider() {
        return rider;
    }
    
    public Location getPickUp() {
        return pickUp;
    }
    
    public Location getDropOff() {
        return dropOff;
    }
}

interface DriverMatchingStrategy {
    Driver findNearestDriver(Location riderLoc, List<Driver> drivers);
}

class NearestDriverStrategy implements DriverMatchingStrategy {
    public Driver findNearestDriver(Location riderLoc, List<Driver> drivers) {
        Driver nearest = null;
        int minDistance = Integer.MAX_VALUE;
        for(Driver driver: drivers) {
            if(driver.getAvailability() == AvailabilityStatus.AVAILABLE) {
                int dist = riderLoc.distanceTo(driver.getLocation());
                if(dist < minDistance) {
                    minDistance = dist;
                    nearest = driver;
                }
            }
        }
        return nearest;
    }
}

class RideService {
    private final List<Driver> allDrivers;
    private final DriverMatchingStrategy matchingStrategy;
    
    public RideService(List<Driver> drivers, DriverMatchingStrategy strategy) {
        this.allDrivers = drivers;
        this.matchingStrategy = strategy;
    }
    
    public Ride bookRide(Rider rider, Location pickUp, Location dropOff) {
        Driver driver = matchingStrategy.findNearestDriver(pickUp, allDrivers);
        if(driver == null) throw new RuntimeException("No available driver found.");
        
        Ride ride = new Ride(rider, driver, pickUp, dropOff);
        driver.setAvailability(AvailabilityStatus.UNAVAILABLE);
        rider.addRideToHistory(ride);
        driver.addRideToHistory(ride);
        
        return ride;
    }
}

public class Main
{
	public static void main(String[] args) {
		Rider r1 = new Rider("r1", "Srikar");
        Driver d1 = new Driver("d1", "Amit", new Location(2, 3));
        Driver d2 = new Driver("d2", "Nina", new Location(5, 1));

        List<Driver> drivers = Arrays.asList(d1, d2);
        RideService service = new RideService(drivers, new NearestDriverStrategy());

        Ride ride = service.bookRide(r1, new Location(3, 3), new Location(8, 8));

        System.out.println("Ride booked with driver: " + ride.getDriver().getName());
	}
}
