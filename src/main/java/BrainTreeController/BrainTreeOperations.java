package BrainTreeController;

import Model.TransactionClient;
import com.braintreegateway.*;
import org.json.JSONObject;

import java.math.BigDecimal;

public class BrainTreeOperations {

    public static JSONObject generateClientToken(BrainTreeClient client){
        JSONObject ret= new JSONObject();
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                client.getMerchant_id(),
                client.getPublic_key(),
                client.getPrivate_key()
        );
        try {
            ret.put("client_token", gateway.clientToken().generate());
        }catch(Exception e){
            e.printStackTrace();

        }
        return ret;
    }


    public static  BraintreeGateway createGateway(BrainTreeClient client){
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                client.getMerchant_id(),
                client.getPublic_key(),
                client.getPrivate_key()
        );
        return gateway;
    }

    public static JSONObject createTransaction(TransactionClient client) {
        JSONObject ret = new JSONObject();
        BigDecimal decimalAmount = new BigDecimal("0");

        try {
            decimalAmount = new BigDecimal(client.getAmount());
            if(client.getMerchant_id()==null||client.getPrivate_key()==null|| client.getPublic_key()==null|| client.getAmount()==null||
                    client.getPayment_method_nonce()==null){
                throw new Exception("Missing Parameter");

            }
        } catch (Exception e) {
            try {
                e.printStackTrace();
                return ret.put("Error", e.toString());
            } catch (Exception f) {
                f.printStackTrace();
            }
        }

        TransactionRequest request = new TransactionRequest()
                .amount(decimalAmount)
                .paymentMethodNonce(client.getPayment_method_nonce())
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = BrainTreeOperations.createGateway(client).transaction().sale(request);
try{
        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            ret.put("Transaction_id", transaction.getId());
            return ret;
        } else if (result.getTransaction() != null) {
            Transaction transaction = result.getTarget();

            ret.put("Transaction_id", transaction.getId());
            return ret;
        } else {
            String errorString = "";
            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                errorString += "Error: " + error.getCode() + ": " + error.getMessage() + "\n";
            }
            ret.put("Error", errorString);
            return ret;
        }
    }catch(Exception e){
        e.printStackTrace();
        }
        return ret;
    }

    public static JSONObject getTransaction(BrainTreeTransaction trans){
        Transaction transaction;
        CreditCard creditCard;
        Customer customer;
        JSONObject  transObj= new JSONObject();

        try {
            if(trans.getMerchant_id()==null||trans.getPrivate_key()==null|| trans.getPublic_key()==null||
                    trans.getTransaction_id()==null){
                throw new Exception("Missing Parameter");

            }
            transaction =BrainTreeOperations.createGateway(trans).transaction().find(trans.getTransaction_id());
            creditCard = transaction.getCreditCard();
            customer = transaction.getCustomer();
            transObj.put("transaction",transaction.toString());
            transObj.put("credit_card",creditCard.toString());
            transObj.put("customer",customer.toString());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            try{
                transObj.put("Error",e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }
        return transObj;
    }


}
