package Model.DataObjects;

public class CompanyPaymentCard {
    private int company_id;
    private String company_token;

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getCompany_token() {
        return company_token;
    }

    public void setCompany_token(String company_token) {
        this.company_token = company_token;
    }

    @Override
    public boolean equals(Object obj) {
        CompanyPaymentCard account= (CompanyPaymentCard) obj;
        if(company_token.equals(account.getCompany_token())&&
                company_id==account.getCompany_id()){
            return true;
        }

        return false;
    }

}
