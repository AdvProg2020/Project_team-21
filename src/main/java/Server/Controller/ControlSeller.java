package Server.Controller;

import Server.Model.*;
import Server.Model.Account.Seller;
import Server.Model.Request.*;
import Server.ServerCenter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControlSeller {
    private String offToView;
    private String offToEdit;
    private String productToView;
    private String productViewBuyers;
    private String productToEdit;
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

    public String getOffToView() {
        return offToView;
    }

    public void setOffToView(String offToView) {
        this.offToView = offToView;
    }

    public String getOffToEdit() {
        return offToEdit;
    }

    public void setOffToEdit(String offToEdit) {
        this.offToEdit = offToEdit;
    }

    public void setProductToView(String productToView) {
        this.productToView = productToView;
    }

    public void setProductToEdit(String productToEdit) {
        this.productToEdit = productToEdit;
    }

    public String getProductToEdit() {
        return productToEdit;
    }

    public String getProductToView() {
        return productToView;
    }

    public String getProductViewBuyers() {
        return productViewBuyers;
    }

    public void setProductViewBuyers(String productViewBuyers) {
        this.productViewBuyers = productViewBuyers;
    }

    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber, Company company, String photo)
    {
        new SellerRequest(Control.getInstance().randomString(10),username,firstName,lastName,email,phoneNumber,password,company, RequestType.ADD,photo);
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
    public String sendProductEditReq(String productID , String field , String value,String token) throws Exception
    {
        String requestID = Control.getInstance().randomString(10);
        Seller user = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
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
        ProductRequest req = new ProductRequest(requestID,"","",null,0,null,user,RequestType.EDIT,null,product,"");
        req.setEditField(field);
        req.setNewValueEdit(value);
        return requestID;
    }
    public String sendAddProductReq(String name, String companyName, String categoryName, String price,String companyLocation,String productID,String imagePath,String token) throws Exception
    {
        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
        Category category = null;
        Company company = null;
        double priceNum = 0;

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
        new ProductRequest(requestID,productID,name,company,priceNum,category,seller,RequestType.ADD,seller,null,imagePath);
        return requestID;
    }
    public String sendAddSellerProductReq(String productID,String token) throws Exception
    {
        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
        if(!Product.getAllProducts().containsKey(productID))
        {
            throw new Exception("This product doesn't exist!");
        }
        if(seller.getAllProducts().contains(Product.getAllProducts().get(productID)))
        {
            throw new Exception("You Already have this product!");
        }
        String requestID = Control.getInstance().randomString(10);
        Product product = Product.getAllProducts().get(productID);
        new ProductRequest(requestID,productID,"",null,0,null,seller,RequestType.ADD_SELLER,seller,product,"");
        return requestID;
    }
    public String sendRemoveProductReq(String productID,String token) throws Exception
    {
        if(!ControlSeller.getInstance().checkProductExists(productID)) {
            throw new Exception("This product doesn't exist! So you can see it as a deleted one :)");
        }
            Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
            Product product = Product.getAllProducts().get(productID);
            String requestID = Control.getInstance().randomString(10);
            new ProductRequest(requestID,productID,"",null,0,null,seller,RequestType.DELETE,seller,product,"");
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
    public String sendEditOffRequest(String offID,String field,String value,String productID,String token) throws Exception
    {
        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
        String requestID = Control.getInstance().randomString(10);
        if(!productID.equalsIgnoreCase("null") && !Product.getAllProducts().containsKey(productID))
        {
            throw new Exception("This product doesn't exist!");
        }
        if(!field.matches("add product|remove product|start|end|amount"))
        {
            throw new Exception("The field is wrong!");
        }
        if(field.matches("end|start") && !Control.getInstance().timeCorrectMatch(value))
        {
            throw new Exception("Time format is wrong!");
        }
        if(field.equalsIgnoreCase("amount") && !Control.getInstance().doubleCheck(value))
        {
            throw new Exception("Amount format is wrong!");
        }
        if(!productID.equalsIgnoreCase("null") && !seller.getAllProducts().contains(Product.getAllProducts().get(productID)))
        {
            throw new Exception("You don't have this product!");
        }
        OffRequest req = new OffRequest(requestID,offID,null,null,null,0,seller,RequestType.EDIT);
        req.setEditField(field);
        req.setNewValueForField(value);
        if(field.matches("add product|remove product"))
        {
            req.setProduct(Product.getAllProducts().get(productID));
        }
        return requestID;
    }
    private int makeInt(String string)
    {
        return Integer.parseInt(string);
    }
    public String sendAddOfRequest(ArrayList<Product> products,String amount,String start,String end,String token) throws Exception
    {
        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
        String requestID = Control.getInstance().randomString(10);
        String offID = Control.getInstance().randomString(10);
        if(!Control.getInstance().timeCorrectMatch(start) || !Control.getInstance().timeCorrectMatch(end))
        {
            throw new Exception("Your time format is wrong!");
        }
        if(!Control.getInstance().doubleCheck(amount))
        {
            throw new Exception("Your amount format is wrong!");
        }
        if(Double.parseDouble(amount) > 100){
            throw new Exception("Your percentage is above 100%");
        }
        String[] startParse = start.split("\\s+");
        String[] endParse = end.split("\\s+");
        LocalDateTime startTime = LocalDateTime.of(makeInt(startParse[0]),makeInt(startParse[1]),makeInt(startParse[2]),makeInt(startParse[3]),makeInt(startParse[4]));
        LocalDateTime endTime = LocalDateTime.of(makeInt(endParse[0]),makeInt(endParse[1]),makeInt(endParse[2]),makeInt(endParse[3]),makeInt(endParse[4]));
        new OffRequest(requestID,offID,products,startTime,endTime,Double.parseDouble(amount),seller,RequestType.ADD);
        return requestID;
    }
    public void createAuction(int year,int month,int day,int hour,int minute,String productId,Seller seller)throws Exception{
        if(!Product.getAllProducts().containsKey(productId))
            throw new Exception("This product doesn't exist.");
        if(!seller.getAllProducts().contains(Product.getAllProducts().get(productId)))
            throw new Exception("You don't have this product.");
        LocalDateTime end = LocalDateTime.of(year,month,day,hour,minute);
        if(end.isBefore(LocalDateTime.now()))
            throw new Exception("Your end date should be after now.");
        new Auction(Control.getInstance().randomString(5),Product.getAllProducts().get(productId),LocalDateTime.now(),end,seller);
    }
}
