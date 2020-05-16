package Controller;

import Model.Account.Seller;
import Model.Company;
import Model.Request.Request;
import Model.Request.RequestType;
import Model.Request.SellerRequest;
import jdk.jfr.Frequency;

public class ControlSeller {
    private static ControlSeller instance;
    private ControlSeller()
    {

    }
    public static ControlSeller getInstance()
    {
        if(instance == null)
            instance = new ControlSeller();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber, Company company)
    {
        new SellerRequest(Control.getInstance().randomString(10),username,firstName,lastName,email,phoneNumber,password,company, RequestType.ADD);
    }

}
