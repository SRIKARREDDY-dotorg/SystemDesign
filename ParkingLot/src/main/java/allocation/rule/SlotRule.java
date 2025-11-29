package allocation.rule;

import model.Vehicle;
import parking.ParkingSpot;

public interface SlotRule {
    boolean isValid(Vehicle v, ParkingSpot spot);
}
