package org.hotel.management;

public class Room {
    private final String id;
    private RoomStatus status;
    private final double price;
    private final RoomType type;

    public Room(String id, double price, RoomType type) {
        this.id = id;
        this.status = RoomStatus.AVAILABLE;
        this.price = price;
        this.type = type;
    }

    public synchronized void book() {
        if (status == RoomStatus.AVAILABLE) {
            System.out.println("The status of the Room: " + status);
            status = RoomStatus.BOOKED;
        } else {
            throw new IllegalStateException("Room is not available for booking.");
        }
    }

    public synchronized void checkIn() {
//        System.out.println("The status of the Room" + status);
        if (status == RoomStatus.BOOKED) {
            status = RoomStatus.OCCUPIED;
        } else {
            throw new IllegalStateException("Room is not booked.");
        }
    }

    public synchronized void checkOut() {
        if (status == RoomStatus.OCCUPIED) {
            status = RoomStatus.AVAILABLE;
        } else {
            throw new IllegalStateException("Room is not occupied.");
        }
    }
    // getters
    public String getId() {
        return id;
    }
    public RoomStatus getStatus() {
        return status;
    }
    public double getPrice() {
        return price;
    }
    public RoomType getType() {
        return type;
    }
}
