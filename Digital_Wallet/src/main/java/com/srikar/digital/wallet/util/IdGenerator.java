package com.srikar.digital.wallet.util;

/**
 * Generates Id for the entire Digital Wallet system
 */
public class IdGenerator {
    public static String generatePaymentMethodId() {
        return "PM-" + java.util.UUID.randomUUID().toString();
    }
    public static String generateTransactionId() {
        return "TXN-" + java.util.UUID.randomUUID().toString();
    }
    public static String generateUserId() {
        return "USR-" + java.util.UUID.randomUUID().toString();
    }
}
