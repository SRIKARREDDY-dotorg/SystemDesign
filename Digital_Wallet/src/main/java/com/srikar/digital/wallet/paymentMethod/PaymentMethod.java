package com.srikar.digital.wallet.paymentMethod;

import com.srikar.digital.wallet.util.IdGenerator;

public abstract class PaymentMethod {
    private final String id;
    private final PaymentMethodType category;
    private final String name;
    private boolean isDefaultPaymentMethod;

    public PaymentMethod(PaymentMethodType category, String name) {
        this.id = IdGenerator.generatePaymentMethodId();
        this.category = category;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public PaymentMethodType getCategory() {
        return category;
    }
    public boolean isDefault() {
        return isDefaultPaymentMethod;
    }
    public void setDefault(boolean isDefault) {
        isDefaultPaymentMethod = isDefault;
    }
}
