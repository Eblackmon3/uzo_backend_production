package StripeController;

import Model.DataObjects.CompanyPaymentCard;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class StripeController {

    public StripeController(){
        Stripe.apiKey=System.getenv("STRIPE_SECRET");
    }

    public JSONObject getKey(){
        JSONObject key= new JSONObject();
        System.out.println(System.getenv("STRIPE_SECRET"));
        try{
            key.put("key",System.getenv("STRIPE_SECRET"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return key;

    }

    public static String createCustomer(CompanyPaymentCard card ){
        Stripe.apiKey=System.getenv("STRIPE_SECRET");
        try {
            // Create a Customer:
            Map<String, Object> chargeParams = new HashMap<>();
            System.out.println(card.getToken_id());
            chargeParams.put("source", card.getToken_id());
            Customer customer = Customer.create(chargeParams);
            System.out.println(card.getToken_id());
            return customer.getId();

        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
