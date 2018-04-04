package Model.DataObjects;

import org.json.JSONArray;

public class CompanyPaymentCard {
    private int company_id;
    private JSONArray company_token;
    private String token_id;

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_token(JSONArray company_token) {
        this.company_token = company_token;
    }

    public JSONArray getCompany_token() {
        return company_token;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    @Override
    public boolean equals(Object obj) {
        CompanyPaymentCard account= (CompanyPaymentCard) obj;
        if(token_id.equals(account.getCompany_token())&&
                company_id==account.getCompany_id()){
            return true;
        }

        return false;
    }

}
