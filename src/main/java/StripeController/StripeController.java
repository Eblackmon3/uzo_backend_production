package StripeController;

import Model.DataManagers.CompanyManager;
import Model.DataObjects.Company;
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


    public JSONObject getKey(){
        JSONObject key= new JSONObject();
        try{
            key.put("key",System.getenv("STRIPE_PUBLISH_KEY"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return key;

    }

    public static String createCustomer(CompanyPaymentCard card ){
        Stripe.apiKey=System.getenv("STRIPE_SECRET_KEY");
        JSONObject companyInfo= new JSONObject();
        Company company = new Company();
        try {
            CompanyManager manager= new CompanyManager();
            company.setCompany_id(card.getCompany_id());
            companyInfo= manager.getCompanyById(company);
            // Create a Customer:
            Map<String, Object> chargeParams = new HashMap<>();
            System.out.println(card.getToken_id());
            chargeParams.put("source", card.getToken_id());
            chargeParams.put("email", companyInfo.get("email"));
            chargeParams.put("description", companyInfo.get("description"));
            Customer customer = Customer.create(chargeParams);
            System.out.println(card.getToken_id());
            return customer.getId();

        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
