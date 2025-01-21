package com.srikar.digital.wallet.paymentMethod;

public class GiftCard extends PaymentMethod {
    private final String giftCardNumber;
    private final String giftCardPin;
    public GiftCard(PaymentMethodType category, String name, String giftCardNumber, String giftCardPin) {
        super(category, name);
        this.giftCardNumber = giftCardNumber;
        this.giftCardPin = giftCardPin;
    }
    public String getGiftCardNumber() {
        return giftCardNumber;
    }
    public String getGiftCardPin() {
        return giftCardPin;
    }
}
