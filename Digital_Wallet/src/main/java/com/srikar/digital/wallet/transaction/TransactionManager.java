package com.srikar.digital.wallet.transaction;

import com.srikar.digital.wallet.User;
import com.srikar.digital.wallet.paymentMethod.PaymentMethod;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionManager {
    private Map<String, List<Transaction>> transactionHistory;
    private static TransactionManager instance;
    private TransactionManager() {
        transactionHistory = new ConcurrentHashMap<>();
    }
    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }
    public String getStatement(User userId, Long limit) {
        List<Transaction> transactions = transactionHistory.get(userId);
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for user: ").append(userId).append("\n");
        for (Transaction transaction : transactions) {
            statement.append(transaction.toString()).append("\n");
        }
        return statement.toString();
    }
    public void transferAmount(String fromUserId, String toUserId, PaymentMethod paymentMethod, double amount) {
        PaymentStatus status;
        if(amount > 0) {
            // Add amount to the recipient's account, needs integration with Payment Services
            // assume successful completion.
            status = PaymentStatus.SUCCESS;
        } else {
            status = PaymentStatus.FAILURE;
        }
        Transaction transaction = new Transaction(fromUserId, toUserId, amount, paymentMethod);
        transaction.setPaymentStatus(status);
        transactionHistory.computeIfAbsent(fromUserId, k -> new java.util.ArrayList<>()).add(transaction);
        transactionHistory.computeIfAbsent(toUserId, k -> new java.util.ArrayList<>()).add(transaction);
    }
}
