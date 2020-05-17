package Controller;

import Model.Account.Seller;
import Model.Category;
import Model.Company;
import Model.Off;
import Model.Product;
import Model.Request.ProductRequest;
import Model.Request.Request;
import Model.Request.RequestType;
import Model.Request.SellerRequest;
import View.ConsoleView;
import jdk.jfr.Frequency;

import java.util.zip.CheckedOutputStream;

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
    public String sendAddProductReq(String name, String companyName, String categoryName, String price,String companyLocation) throws Exception
    {
        Seller seller = (Seller) Control.getInstance().getUser();
        Category category = null;
        Company company = null;
        double priceNum = 0;
        String productID = Control.getInstance().randomString(10);
        String requestID = Control.getInstance().randomString(10);
        for (Category category1 : Category.getAllCategories()) {
            if(category1.getName().equalsIgnoreCase(categoryName))
                category = category1;
        }
        if(category == null)
        {
            throw new Exception("Category wasn't found");
        }
        if(Company.getAllCompanies().containsKey(companyName))
        {
            company = Company.getAllCompanies().get(companyName);
        }
        else
        {
            company = new Company(companyName,companyLocation);
        }
        if(!price.matches("\\d+.?\\d*"))
        {
            throw new Exception("Your price format is wrong!");
        }
        else
        {
            priceNum = Double.parseDouble(price);
        }
        new ProductRequest(requestID,productID,name,company,priceNum,category,seller,RequestType.ADD,seller,null);
        return requestID;
    }
    public String sendAddSellerProductReq(String productID) throws Exception
    {
        if(!Product.getAllProducts().containsKey(productID))
        {
            new Exception("This product doesn't exist!");
        }
        String requestID = Control.getInstance().randomString(10);
        Seller seller = (Seller) Control.getInstance().getUser();
        Product product = Product.getAllProducts().get(productID);
        new ProductRequest(requestID,productID,"",null,0,null,seller,RequestType.ADD_SELLER,seller,product);
        return requestID;
    }
    public String sendRemoveProductReq(String productID) throws Exception
    {
        if(!ControlSeller.getInstance().checkProductExists(productID)) {
            throw new Exception("This product doesn't exist! So you can see it as a deleted one :)");
        }
            Seller seller = (Seller) Control.getInstance().getUser();
            Product product = Product.getAllProducts().get(productID);
            String requestID = Control.getInstance().randomString(10);
            new ProductRequest(requestID,productID,"",null,0,null,seller,RequestType.DELETE,seller,product);
        return requestID;
    }
    public boolean checkOffExistance (String offID)
    {
        if(Off.getAllOffs().containsKey(offID))
            return true;
        return false;
    }
    public boolean checkSellerGotOff (String offID , Seller seller)
    {
        for (Off off : seller.getAllOffs()) {
            if(off.getOffId().equals(offID))
                return true;
        }
        return false;
    }
    public String sendAddOffRequest()
    {
        String requestID = Control.getInstance().randomString(10);
        String offID = Control.getInstance().randomString(10);
        Seller seller = (Seller) Control.getInstance().getUser();
        if(ControlSeller.getInstance().checkOffExistance(offID) && ControlSeller.getInstance().checkSellerGotOff(offID,seller))
        {

            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            ConsoleView.getInstance().errorInput("You don't have this off!" , ConsoleView.getInstance().getLastMenu());
        }
        return requestID;
    }

}
