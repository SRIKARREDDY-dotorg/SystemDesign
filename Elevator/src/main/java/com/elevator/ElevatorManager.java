package com.elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {
    private static ElevatorManager instance;
    private final List<Elevator> elevators;

    private ElevatorManager(int numOfElevators, int capacity) {
        elevators = new ArrayList<>();
        for (int i = 0; i < numOfElevators; i++) {
            Elevator elevator = new Elevator(i, capacity);
            elevators.add(elevator);
            new Thread(elevator::run).start();
        }
    }

    public static ElevatorManager getInstance(int numOfElevators, int capacity) {
        if (instance == null) {
            instance = new ElevatorManager(numOfElevators, capacity);
        }
        return instance;
    }

    public void requestElevator(int sourceFloor, int destinationFloor) {
        Elevator optimalElevator = findOptimalElevator(sourceFloor, destinationFloor);
        optimalElevator.addRequest(new Request(sourceFloor, destinationFloor));
    }

    private Elevator findOptimalElevator(int sourceFloor, int destinationFloor) {
        Elevator optimalElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(sourceFloor - elevator.getCurrentFloor());
            if (distance < minDistance) {
                minDistance = distance;
                optimalElevator = elevator;
            }
        }

        return optimalElevator;
    }
}
