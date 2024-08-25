package org.taskmanage.payment;

public class PayPalPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        // Processing payment using PayPal
        System.out.println("Processing payment of $" + amount + " using PayPal");
        return true;
    }
}
