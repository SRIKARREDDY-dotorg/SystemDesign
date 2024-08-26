package org.atm;

public class CashDispenser {
    private double cashAvailable;

    public CashDispenser(double cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public void dispenseCash(double amount) {
        if (cashAvailable >= amount) {
            cashAvailable -= amount;
            System.out.println("Dispensing cash: " + amount);
        } else {
            throw new IllegalStateException("Insufficient cash available in ATM");
        }
    }
}
