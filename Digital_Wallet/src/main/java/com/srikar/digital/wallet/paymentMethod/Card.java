package com.srikar.digital.wallet.paymentMethod;

import java.util.Date;

public class Card extends PaymentMethod {
    private final String cardNumber;
    private final Date expiryDate;
    private final Long cvv;

    public Card(PaymentMethodType category, String name, String cardNumber, Date expiryDate, Long cvv) {
        super(category, name);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }
    public Long getCvv() {
        return cvv;
    }
}
