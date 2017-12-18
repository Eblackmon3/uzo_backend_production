package Model;

public class Company {
    private int company_id;
    private String email;
    private String address;
    private String website_link;
    private String company_name;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Company Email: "+ " Company Name: "+ company_name + " Company Website"+website_link;
    }

    @Override
    public boolean equals(Object obj) {
        Company comp=  (Company) obj;
        if(((Company) obj).getCompany_id()==company_id){
            return true;
        }
        return false;
    }
}