package com.srikar.digital.wallet.paymentMethod;

public class UPI extends PaymentMethod {
    private final String upiId;
    private final String upiPin;
    public UPI(PaymentMethodType category, String name, String upiId, String upiPin) {
        super(category, name);
        this.upiId = upiId;
        this.upiPin = upiPin;
    }
    public String getUpiId() {
        return upiId;
    }
    public String getUpiPin() {
        return upiPin;
    }
}
