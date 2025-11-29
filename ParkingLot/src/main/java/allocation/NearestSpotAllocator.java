package allocation;

import allocation.rule.SlotRule;
import model.Vehicle;
import parking.ParkingFloor;
import parking.ParkingSpot;

import java.util.List;

public class NearestSpotAllocator implements SpotAllocator {
    private final List<ParkingFloor> floors;
    private final List<SlotRule> rules;
    public NearestSpotAllocator(List<ParkingFloor> floors, List<SlotRule> rules) {
        this.floors = floors;
        this.rules = rules;
    }
    @Override
    public ParkingSpot findSpot(Vehicle vehicle) {
        for(ParkingFloor floor : floors) {
            for(ParkingSpot spot : floor.getSpots()) {
                if(!spot.isFree()) continue;
                boolean ok = true;
                for(SlotRule rule : rules) {
                    if(!rule.isValid(vehicle, spot)) {
                        ok = false;
                        break;
                    }
                }

                if(ok) {
                    return spot;
                }
            }
        }
        return null;
    }
}
