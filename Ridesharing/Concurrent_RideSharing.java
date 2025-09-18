// Ride Sharing with concurrency handling...


// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

class RideSharingPlatform {
    public static void main(String[] args) throws InterruptedException {
        DriverPool pool = new DriverPool();
        pool.addDriver(new Driver("d1", "Alice"));
        pool.addDriver(new Driver("d2", "Bob"));
        pool.addDriver(new Driver("d3", "Charlie"));
        
        MatchingService matcher = new MatchingService(pool);
        // RideManager rideManager = new RideManager();
        
        // Rider r1 = new Rider("r1", "John");
        // Rider r2 = new Rider("r2", "Mary");
        
        // RideRequest req1 = new RideRequest("req1", r1);
        // RideRequest req2 = new RideRequest("req2", r2);
        
        // ExecutorService exec = Executors.newFixedThreadPool(10);
        // exec.submit(() -> matcher.matchDriver(req1));
        // exec.submit(() -> matcher.matchDriver(req2));
        
        // Thread.sleep(1000);
        
        // rideManager.startRide(req1);
        // rideManager.startRide(req2);
        // rideManager.completeRide(req1);
        // rideManager.completeRide(req2);
        
        // exec.shutdown();
        // simulateConcurrentRideRequests(matcher);
        simulateCancellationRace(matcher);
    }
    
    private static void simulateCancellationRace(MatchingService matcher) throws InterruptedException {
        Rider rider = new Rider("r100", "CancelRider");
        RideRequest req = new RideRequest("req100", rider);
        
        ExecutorService exec = Executors.newFixedThreadPool(2);
        
        exec.submit(() -> matcher.matchDriver(req));
        exec.submit(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
            synchronized (req) {
                if (req.status == RideStatus.PENDING) {
                    req.status = RideStatus.CANCELLED;
                    System.out.println("🚨 Rider " + rider.name + " cancelled the request!");
                } else {
                    System.out.println("❌ Rider tried to cancel, but ride already " + req.status);
                }
            }
        });
        
        exec.shutdown();
        exec.awaitTermination(2, TimeUnit.SECONDS);
        
        if (req.assignedDriver != null && req.status == RideStatus.CANCELLED) {
            System.out.println("⚠️ Driver " + req.assignedDriver.name + " was reserved for a cancelled ride!");
        }
    }
    
    private static void simulateConcurrentRideRequests(final MatchingService matcher) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        List<RideRequest> requests = new ArrayList<>();
        
        for(int i = 0; i < 10; i ++) {
            Rider rider = new Rider("r" + i, "Rider" + i);
            RideRequest req = new RideRequest("req" + i, rider);
            requests.add(req);
            exec.submit(() -> matcher.matchDriver(req));
        }
        
        exec.shutdown();
        exec.awaitTermination(3, TimeUnit.SECONDS);
        
        Map<Driver, Long> counts = requests.stream()
        .filter(r -> r.assignedDriver != null)
        .collect(Collectors.groupingBy(r -> r.assignedDriver, Collectors.counting()));
  
        
        counts.forEach((driver, count) -> {
            if (count > 1) {
                System.out.println("⚠️ Driver " + driver.name + " assigned to " + count + " requests!");
            }
        });
    }
}

abstract class User {
    String id;
    String name;
}

class Rider extends User {
    public Rider(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

enum DriverStatus {
    AVAILABLE,
    RESERVED,
    ON_TRIP,
    OFFLINE
}

class Driver extends User {
    private final AtomicReference<DriverStatus> status = new AtomicReference<>(DriverStatus.AVAILABLE);
    
    public Driver(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public boolean tryReserve() {
        return status.compareAndSet(DriverStatus.AVAILABLE, DriverStatus.RESERVED);
    }
    
    public boolean startTrip() {
        return status.compareAndSet(DriverStatus.RESERVED, DriverStatus.ON_TRIP);
    }
    
    public void finishTrip() {
        status.set(DriverStatus.AVAILABLE);
    }
    
    public DriverStatus getStatus() {
        return status.get();
    }
}

enum RideStatus {
    PENDING,
    ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

class RideRequest {
    final String requestId;
    final Rider rider;
    RideStatus status = RideStatus.PENDING;
    volatile Driver assignedDriver;
    
    public RideRequest(String requestId, Rider rider) {
        this.requestId = requestId;
        this.rider = rider;
    }
}

class DriverPool {
    private final ConcurrentMap<String, Driver> availableDrivers = new ConcurrentHashMap<>();
    
    public void addDriver(Driver d) {
        availableDrivers.put(d.id, d);
    }
    public void removeDriver(Driver d) {
        availableDrivers.remove(d.id);
    }
    public Collection<Driver> listAvailable() {
        return availableDrivers.values();
    }
}

class MatchingService {
    private final DriverPool driverPool;
    
    public MatchingService(DriverPool pool) {
        this.driverPool = pool;
    }
    
    public Optional<Driver> matchDriver(RideRequest request) {
        // Fair assignment of drivers.
        List<Driver> shuffled = new ArrayList<>(driverPool.listAvailable());
        Collections.shuffle(shuffled, ThreadLocalRandom.current());
        for (Driver d : shuffled) {
            if(d.tryReserve()) {
                synchronized (request) {
                    if(request.status == RideStatus.PENDING) {
                        request.assignedDriver = d;
                        request.status = RideStatus.ASSIGNED;
                        System.out.println("Matched driver " + d.name + " to rider " + request.rider.name);
                        return Optional.of(d);
                    } else {
                        d.finishTrip();
                        System.out.println("⚠️ Driver " + d.name + " reserved but request was " + request.status);
                    }
                }
            }
        }
        System.out.println("No driver found for request " + request.rider.name);
        return Optional.empty();
    }
}

class RideManager {
    public void startRide(RideRequest req) {
        Driver d = req.assignedDriver;
        if(d != null && d.startTrip()) {
            req.status = RideStatus.IN_PROGRESS;
            System.out.println("Ride " + req.requestId + " started with driver " + d.name);
        }
    }
    
    public void completeRide(RideRequest req) {
        Driver d = req.assignedDriver;
        if(d != null) {
            d.finishTrip();
            req.status = RideStatus.COMPLETED;
            System.out.println("Ride " + req.requestId + " completed.");
        }
    }
}





