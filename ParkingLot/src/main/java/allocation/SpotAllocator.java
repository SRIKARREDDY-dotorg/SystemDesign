package allocation;

import model.Vehicle;
import parking.ParkingSpot;

public interface SpotAllocator {
    ParkingSpot findSpot(Vehicle vehicle);
}
