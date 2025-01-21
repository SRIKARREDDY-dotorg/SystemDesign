package com.srikar.digital.wallet.transaction;

import com.srikar.digital.wallet.paymentMethod.PaymentMethod;
import com.srikar.digital.wallet.util.IdGenerator;

public class Transaction {
    private final String transactionId;
    private final String senderId;
    private final String receiverId;
    private final Double amount;
    private PaymentStatus paymentStatus;
    private final PaymentMethod paymentMethod;

    public Transaction(String senderId, String receiverId, Double amount, PaymentMethod paymentMethod) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = IdGenerator.generateTransactionId();
    }
    public String getTransactionId() {
        return transactionId;
    }
    public String getSenderId() {
        return senderId;
    }
    public String getReceiverId() {
        return receiverId;
    }
    public Double getAmount() {
        return amount;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
