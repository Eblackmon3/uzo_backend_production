package StripeController;

import com.stripe.Stripe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

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
}
