package model;

public class Vehicle {
    private final String plate;
    private final VehicleType type;
    private final boolean isElectric;

    public Vehicle(String plate, VehicleType type, boolean isElectric) {
        this.plate = plate;
        this.type = type;
        this.isElectric = isElectric;
    }

    public String getPlate() {
        return plate;
    }
    public VehicleType getType() {
        return type;
    }
    public boolean isElectric() {
        return isElectric;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "plate='" + plate + '\'' +
                ", type=" + type +
                ", is EV=" + isElectric +
                '}';
    }

}
