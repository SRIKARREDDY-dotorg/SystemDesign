package allocation.rule;

import model.Vehicle;
import parking.ParkingSpot;

public class AllowNonEvonEvRule implements SlotRule {
    private final boolean allow;

    public AllowNonEvonEvRule(boolean allow) {
        this.allow = allow;
    }

    @Override
    public boolean isValid(Vehicle v, ParkingSpot spot) {
        if(!v.isElectric() && spot.isEVCapable()) {
            return allow;
        }
        return true;
    }
}
