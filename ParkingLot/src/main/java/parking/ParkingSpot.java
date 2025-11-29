package parking;

import model.EVAttributes;
import model.SpotType;
import model.Vehicle;

public class ParkingSpot {
    private String id;
    private final SpotType type;
    private Vehicle vehicle = null;
    private final EVAttributes evAttributes;
    private boolean evCapable;

    public ParkingSpot(String id, SpotType type, boolean evCapable) {
        this.id = id;
        this.type = type;
        this.evCapable = evCapable;
        this.evAttributes = evCapable ? new EVAttributes() : null;
    }

    public synchronized boolean isFree() {
        return vehicle == null;
    }

    public synchronized boolean assignVehicle(final Vehicle v) {
        if(vehicle != null) return false;
        vehicle = v;
        if(vehicle.isElectric() && evAttributes != null) {
            evAttributes.startChargingIfNeeded();
        }
        return true;
    }

    public synchronized void releaseVehicle() {
        if(vehicle != null) {
            if(vehicle.isElectric() && evAttributes != null) {
                evAttributes.stopChargingIfNeeded();
            }
            vehicle = null;
            if(evAttributes != null) {
                evAttributes.clear();
            }
        }
    }

    public String getId() {
        return id;
    }

    public SpotType getType() {
        return type;
    }

    public boolean isEVCapable() {
        return evCapable;
    }

    public EVAttributes getEvAttributes() {
        return evAttributes;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", available=" + isFree() +
                ", evAttributes=" + evAttributes +
                ", evCapable=" + evCapable +
                '}';
    }
}
