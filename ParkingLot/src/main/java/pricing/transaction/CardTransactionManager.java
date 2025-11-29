package pricing.transaction;

public class CardTransactionManager implements TransactionManager {
    @Override
    public boolean initiatePayment(double amount) {
        // simulate success (in real integrate with gateway)
        return true;
    }
}
