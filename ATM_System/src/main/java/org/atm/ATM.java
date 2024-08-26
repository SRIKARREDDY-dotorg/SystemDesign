package org.atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class ATM {
    private final BankingService bankingService;
    private final CashDispenser cashDispenser;
    private static final AtomicLong transactionCounter = new AtomicLong(0);

    public ATM(BankingService bankingService, CashDispenser cashDispenser) {
        this.bankingService = bankingService;
        this.cashDispenser = cashDispenser;
    }

    public void authenticate(Card card) {
        // authenticate user based on card details
    }

    public double checkBalance(String accountNumber) {
        // retrieve and display account balance\
        Account account = bankingService.getAccount(accountNumber);
        return account.getBalance();
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = bankingService.getAccount(accountNumber);
        Transaction transaction = new WithdrawTransaction(account, amount, generateTransactionId());
        bankingService.performTransaction(transaction);
        cashDispenser.dispenseCash(amount);
    }

    public void deposit(String accountNumber, double amount) {
        Account account = bankingService.getAccount(accountNumber);
        Transaction transaction = new DepositTransaction(account, amount, generateTransactionId());
        bankingService.performTransaction(transaction);
    }

    private String generateTransactionId() {
        long transactionNumber = transactionCounter.incrementAndGet();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "TXN" + timestamp + String.format("%010d", transactionNumber);
    }
}
