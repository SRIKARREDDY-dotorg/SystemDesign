package com.srikar.digital.wallet.paymentMethod;

public class PaymentMethodFactory {
    public static PaymentMethod createPaymentMethod(PaymentMethodType category, String name, String... details) {
        switch (category) {
            case UPI:
                return new UPI(category, name, details[0], details[1]);
            case GIFT_CARD:
                return new GiftCard(category, name, details[0], details[1]);
            case CARD:
                return new Card(category, name, details[0], java.sql.Date.valueOf(details[1]), Long.parseLong(details[2]));
            default:
                throw new IllegalArgumentException("Invalid payment method category");
        }
    }
}
