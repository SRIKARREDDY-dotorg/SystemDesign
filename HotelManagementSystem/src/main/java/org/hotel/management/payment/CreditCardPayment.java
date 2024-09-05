package org.hotel.management.payment;

public class CreditCardPayment implements Payment {
    @Override
    public boolean processPayment(double amount) {
        // process payment
        return true;
    }
}
