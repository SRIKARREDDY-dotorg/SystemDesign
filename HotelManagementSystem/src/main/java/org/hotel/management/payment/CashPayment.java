package org.hotel.management.payment;

public class CashPayment implements Payment {
    @Override
    public boolean processPayment(double amount) {
        // process payment logic
        return true;
    }
}
