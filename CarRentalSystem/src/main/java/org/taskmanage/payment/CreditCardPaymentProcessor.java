package org.taskmanage.payment;

public class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        // process payment
        System.out.println("Processing payment of $" + amount + " using Credit Card");
        return true;
    }
}
