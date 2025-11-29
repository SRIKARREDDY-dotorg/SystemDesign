package allocation.rule;

import model.Vehicle;
import parking.ParkingSpot;

public class EVRequiredRule implements SlotRule {
    @Override
    public boolean isValid(Vehicle v, ParkingSpot spot) {
        if(v.isElectric()) {
            return spot.isEVCapable();
        }
        return true;
    }
}
