package org.atm;

public class WithdrawTransaction extends Transaction {
    public WithdrawTransaction(Account account, double amount, String transactionId) {
        super(account, amount, transactionId);
    }

    @Override
    public void execute() {
        account.withdraw(amount);
    }
}
