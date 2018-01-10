package Model;

public class TransactionClient extends BrainTreeClient {
    private String amount;
    private String payment_method_nonce;

    public void setPayment_method_nonce(String payment_method_nonce) {
        this.payment_method_nonce = payment_method_nonce;
    }

    public String getPayment_method_nonce() {
        return payment_method_nonce;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
