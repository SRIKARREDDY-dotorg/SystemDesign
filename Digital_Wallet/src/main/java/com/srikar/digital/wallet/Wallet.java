package com.srikar.digital.wallet;

import com.srikar.digital.wallet.paymentMethod.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private List<PaymentMethod> paymentMethods;

    public Wallet() {
        this.paymentMethods = new ArrayList<>();
    }
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
