package com.elevator;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ElevatorManager elevatorManager = ElevatorManager.getInstance(3, 10);
        elevatorManager.requestElevator(5, 10);
        elevatorManager.requestElevator(3, 7);
        elevatorManager.requestElevator(8, 2);
        elevatorManager.requestElevator(1, 9);
    }
}