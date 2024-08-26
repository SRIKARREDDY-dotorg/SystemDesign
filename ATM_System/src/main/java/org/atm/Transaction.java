package org.atm;

public abstract class Transaction {
    protected final Account account;
    protected final double amount;
    protected final String transactionId;

    public Transaction(Account account, double amount, String transactionId) {
        this.account = account;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public abstract void execute();
}
