import allocation.NearestSpotAllocator;
import allocation.SpotAllocator;
import allocation.rule.AllowNonEvonEvRule;
import allocation.rule.EVRequiredRule;
import allocation.rule.SizeRuleImpl;
import allocation.rule.SlotRule;
import model.SpotType;
import model.Vehicle;
import model.VehicleType;
import parking.ParkingFloor;
import parking.ParkingLot;
import parking.ParkingSpot;
import pricing.BaseHourlyPricingRule;
import pricing.LostTicketRule;
import pricing.PricingEngine;
import pricing.PricingRule;
import pricing.transaction.CardTransactionManager;
import pricing.transaction.TransactionManager;
import service.EntryService;
import service.ExitService;
import service.ParkingManager;
import ticket.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ParkingLotDemo {
    public static void main(String[] args) throws Exception {
        // Build floors & spots (proximity: floor1 spots first)
        ParkingFloor floor1 = new ParkingFloor("F1");
        floor1.addSpot(new ParkingSpot("F1-S1", SpotType.SMALL, false));
        floor1.addSpot(new ParkingSpot("F1-S2", SpotType.MEDIUM, false));
        floor1.addSpot(new ParkingSpot("F1-S3", SpotType.MEDIUM, true)); // EV capable
        floor1.addSpot(new ParkingSpot("F1-S4", SpotType.LARGE, false));

        ParkingFloor floor2 = new ParkingFloor("F2");
        floor2.addSpot(new ParkingSpot("F2-S1", SpotType.SMALL, false));
        floor2.addSpot(new ParkingSpot("F2-S2", SpotType.MEDIUM, true)); // EV capable
        floor2.addSpot(new ParkingSpot("F2-S3", SpotType.LARGE, false));

        List<ParkingFloor> floors = List.of(floor1, floor2);

        // slot rules
        List<SlotRule> rules = List.of(new SizeRuleImpl(), new EVRequiredRule(), new AllowNonEvonEvRule(true));

        List<PricingRule> prules = List.of(
                new BaseHourlyPricingRule(10),
                new LostTicketRule(100));
        PricingEngine pricing = new PricingEngine(prules);
        TransactionManager txMgr = new CardTransactionManager();
        SpotAllocator allocator = new NearestSpotAllocator(floors, rules);
        ParkingLot lot = new ParkingLot(floors, allocator);
        EntryService entry = new EntryService(lot);
        ExitService exit = new ExitService(lot, txMgr, pricing);
        ParkingManager manager = new ParkingManager(exit, entry);

        // build vehicles
        List<Vehicle> vehicles = List.of(
                new Vehicle("KA01-0002", VehicleType.CAR, false),
                new Vehicle("KA01-0003", VehicleType.CAR, false),
                new Vehicle("KA01-0004", VehicleType.TRUCK, false),
                new Vehicle("KA01-0005", VehicleType.BIKE, false)
        );

        ExecutorService es = Executors.newFixedThreadPool(8);

        System.out.println("Starting concurrent entry... available spots: " + lot.availableSpotsCount());
        List<Future<Ticket>> futures = new ArrayList<>();
        for(Vehicle v : vehicles) {
            futures.add(es.submit(() -> {
                try {
                    Ticket t = manager.handleEntry(v);
                    System.out.println(Thread.currentThread().getName() + " parked " + v + " -> " + t);
                    return t;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }));
            Thread.sleep(150); // stagger arrivals a bit
        }

        // schedule some exits after delay
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);

        ses.schedule(() -> {
            System.out.println("Attempting checkout for KA01-0002");
            boolean ok = manager.handleExitByPlate("KA01-0002");
            System.out.println("Checkout KA01-0002 => " + ok);
        }, 4, TimeUnit.SECONDS);

        ses.schedule(() -> {
            System.out.println("Attempting checkout for KA01-0003");
            boolean ok = manager.handleExitByPlate("KA01-0003");
            System.out.println("Checkout KA01-0003 => " + ok);
        }, 6, TimeUnit.SECONDS);

        // wait for parkings
        for (Future<Ticket> f : futures) {
            try { f.get(15, TimeUnit.SECONDS); } catch (Exception ignored) {}
        }

        // wait for scheduled checkouts to finish
        Thread.sleep(9000);

        System.out.println("Available spots after ops: " + lot.availableSpotsCount());

        es.shutdownNow();
        ses.shutdownNow();
    }
}
