package allocation.rule;

import model.SpotType;
import model.Vehicle;
import parking.ParkingSpot;

public class SizeRuleImpl implements SlotRule {
    @Override
    public boolean isValid(Vehicle v, ParkingSpot spot) {
        SpotType type = spot.getType();
        switch (v.getType()) {
            case BIKE:
                return type == SpotType.SMALL || type == SpotType.MEDIUM || type == SpotType.LARGE;
            case CAR:
                return type == SpotType.MEDIUM || type == SpotType.LARGE;
            case TRUCK:
                return type == SpotType.LARGE;
            default:
                throw new RuntimeException("Invalid vehicle type");
        }
    }
}
