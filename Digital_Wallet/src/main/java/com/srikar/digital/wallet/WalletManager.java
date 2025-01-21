package com.srikar.digital.wallet;

import com.srikar.digital.wallet.paymentMethod.PaymentMethod;
import com.srikar.digital.wallet.paymentMethod.PaymentMethodType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WalletManager {
    private static WalletManager instance;
    private Wallet wallet;
    private Map<String, PaymentMethod> paymentMethods;
    private PaymentMethod defaultPaymentMethod;

    private WalletManager() {
        paymentMethods = new ConcurrentHashMap<>();
    }

    public static WalletManager getInstance() {
        if (instance == null) {
            instance = new WalletManager();
            instance.wallet = new Wallet();
        }
        return instance;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        wallet.getPaymentMethods().add(paymentMethod);
        paymentMethods.put(paymentMethod.getId(), paymentMethod);
    }
    public void removePaymentMethod(String paymentMethodId) {
        paymentMethods.remove(paymentMethodId);
        wallet.getPaymentMethods().removeIf(paymentMethod -> paymentMethod.getId().equals(paymentMethodId));
    }
    public void selectPaymentMethod(String paymentMethodId) {
        PaymentMethod paymentMethod = paymentMethods.get(paymentMethodId);
        if (paymentMethod != null) {
            if (defaultPaymentMethod != null) {
                defaultPaymentMethod.setDefault(false);
            }
            paymentMethod.setDefault(true);
            defaultPaymentMethod = paymentMethod;
        }
    }
    public PaymentMethod getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }
}
