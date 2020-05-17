package Controller;

import Model.Account.Seller;
import Model.Company;
import Model.Product;
import Model.Request.ProductRequest;
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
    public boolean checkProductExists(String productID)
    {
        if(Product.getAllProducts().containsKey(productID))
            return true;
        return false;
    }
    public boolean checkSellerGotProduct(String productID, Seller seller)
    {
        Product product = Product.getAllProducts().get(productID);
        if(seller.getAllProducts().contains(product))
            return true;
        return false;
    }
    public String sendProductEditReq(String productID , String field , String value) throws Exception
    {
        String requestID = Control.getInstance().randomString(10);
        Seller user = (Seller) Control.getInstance().getUser();
        if(!checkProductExists(productID))
        {
            throw new Exception("This product doesn't exist");
        }
        else if(!checkSellerGotProduct(productID,user))
        {
            throw new Exception("You don't have this product!");
        }
        if(!field.matches("(?i)name|price"))
        {
            throw new Exception("You entered the wrong field");
        }
        if(field.matches("(?i)price"))
        {
            if(!value.matches("\\d+.?\\d*"))
            {
                throw new Exception("Your price value isn't correct");
            }
        }
        Product product = Product.getAllProducts().get(productID);
        ProductRequest req = new ProductRequest(requestID,"","",null,0,null,null,RequestType.EDIT,null,product);
        req.setEditField(field);
        req.setNewValueEdit(value);
        return requestID;
    }
}
