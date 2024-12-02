package com.srikar.library.payment;

// Handle the payments of each user
public interface PaymentManager {
    public void pay(double amount);
    public void refund(double amount);
    public void getPaymentHistory();
}
