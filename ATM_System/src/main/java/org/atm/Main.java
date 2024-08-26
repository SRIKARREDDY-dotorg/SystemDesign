package org.atm;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BankingService bankingService = new BankingService();
        CashDispenser cashDispenser = new CashDispenser(10000.0);
        ATM atm = new ATM(bankingService, cashDispenser);

        bankingService.createAccount("1234567890", 1000.0);
        bankingService.createAccount("9876543210", 500.0);

        // Perform ATM operations
        Card card = new Card("1234567890", "1234");
        atm.authenticate(card);

        double balance = atm.checkBalance("1234567890");
        System.out.println("Account balance: " + balance);

        atm.withdraw("1234567890", 500.0);
        atm.deposit("9876543210", 200.0);

        balance = atm.checkBalance("1234567890");
        System.out.println("Updated account balance: " + balance);
    }
}