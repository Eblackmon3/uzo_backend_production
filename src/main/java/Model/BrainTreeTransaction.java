package Model;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Transaction;

public class BrainTreeTransaction extends BrainTreeClient {
    private Transaction transaction;
    private CreditCard creditCard;
    private Customer customer;
    private String transaction_id;

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    @Override
    public boolean equals(Object obj) {
        BrainTreeTransaction obj1= (BrainTreeTransaction) obj;
        if(creditCard.equals(obj1.getCreditCard())&&customer.equals(obj1.getCreditCard())&& transaction.equals(obj1.getTransaction())){
            return true;
        }else{
            return false;
        }
    }
}
