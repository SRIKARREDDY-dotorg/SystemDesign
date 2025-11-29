package parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingFloor {
    private final String id;
    private final List<ParkingSpot> spots = new ArrayList<>();

    public ParkingFloor(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public List<ParkingSpot> getSpots() {
        return Collections.unmodifiableList(spots);
    }
    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }
}
