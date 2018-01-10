package BrainTreeController;

import Model.BrainTreeClient;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

public class BrainTreeOperations {

    public static String createBrainTreeGateway(BrainTreeClient client){
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                client.getMerchant_id(),
                client.getPublic_key(),
                client.getPrivate_key()
        );
        return gateway.clientToken().generate();
    }


}
