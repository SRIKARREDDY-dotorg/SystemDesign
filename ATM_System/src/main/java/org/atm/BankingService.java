package org.atm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BankingService {
    private final Map<String, Account> accounts;

    public BankingService() {
        this.accounts = new ConcurrentHashMap<>();
    }

    public void createAccount(String accountNumber, double initialBalance) {
        accounts.put(accountNumber, new Account(accountNumber, initialBalance));
    }
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    public void performTransaction(Transaction transaction) {
        transaction.execute();
    }
}
