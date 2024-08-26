package org.atm;

public class DepositTransaction extends Transaction {
    public DepositTransaction(Account account, double amount, String transactionId) {
        super(account, amount, transactionId);
    }

    @Override
    public void execute() {
        account.deposit(amount);
    }
}
