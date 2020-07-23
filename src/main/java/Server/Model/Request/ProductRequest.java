package Server.Model.Request;

import Server.DatabaseHandler;
import Server.Model.*;
import Server.Model.Account.Seller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class ProductRequest extends Request implements Serializable {
    private String provider;
    private String editField;
    private String newValueEdit;
    private String seller;

    public ProductRequest(String requestId, String productId, String name, Company company, double price, Category category, Seller provider, RequestType requestType, Seller seller, Product product,String imagePath)
    {
        super(requestType);
        if(requestType.equals(RequestType.ADD))
            product = new Product(productId,name,company,price,category,provider,imagePath);
        product.setRequestID(requestId);
        requestedProducts.put(requestId,product);
        Request.addRequest(requestId,this);
        this.provider = provider.getUsername();

        setProviderUsername(provider.getUsername());
        setRequestId(requestId);
        SaveData.saveData(product, (getRequestId()+product.getProductId()), SaveData.productRequestFile);
        SaveData.saveData(this, getRequestId(), SaveData.productReqFile);
    }

    public static void rewriteFiles(){
        for (String s : requestedProducts.keySet()) {
            Product product = requestedProducts.get(s);
            SaveData.saveDataRunning(product, s+product.getProductId(), SaveData.productRequestFile);
        }
    }

    public void setEditField(String editField) {
        this.editField = editField;
    }

    public void setNewValueEdit(String newValueEdit) {
        this.newValueEdit = newValueEdit;
    }

    public Seller getProvider() {
        Seller res = null;
        for (Seller seller : Seller.getAllSeller()) {
            if(seller.getUsername().equals(provider))
                res = seller;
        }
        return res;
    }

    @Override
    public void declineReq(String requestId)
    {
        File file = new File("Database/" + requestId +(requestedProducts.get(requestId).getProductId())+".json");
        file.delete();
        File file1 = new File("Database/" + requestId+".json");
        file1.delete();
        requestedProducts.remove(requestId);
        Request.getAllRequests().remove(requestId);
    }

    @Override
    public void acceptReq(String requestId)
    {
        Product product = Request.getRequestedProducts().get(requestId);
        if(this.getRequestType().equals(RequestType.ADD))
        {
            Product.addProduct(product);
        }
        else if(this.getRequestType().equals(RequestType.DELETE))
            Product.removeProduct(product);
        else if(this.getRequestType().equals(RequestType.EDIT))
        {
            if(editField.equalsIgnoreCase("price"))
            {
                product.setPrice(Double.parseDouble(newValueEdit));
            }
            else if(editField.equalsIgnoreCase("name"))
            {
                product.setName(newValueEdit);
            }
        }
        else if(this.getRequestType().equals(RequestType.ADD_SELLER))
        {
            Seller seller = null;
            for (Seller seller1 : Seller.getAllSeller()) {
                if(seller1.getUsername().equals(this.provider))
                    seller = seller1;
            }
            product.addSeller(seller);
            seller.addProduct(product);
        }
        declineReq(requestId);
    }

    @Override
    public String getType() {
        return "Product Request";
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.productRequestFile)));
        for (Object object : objects) {
            getRequestedProducts().put(((Product)object).getRequestID() ,(Product) (object));
        }

        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.productReqFile)));
        for (Object object : objects2) {
            getAllRequests().put(((ProductRequest)object).getRequestId() ,(ProductRequest) (object));
        }
    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Product> products = new ArrayList<>(DatabaseHandler.selectFromProductAtRequest());
        for (Product product : products) {
            requestedProducts.put(product.getRequestID(), product);
        }

        ArrayList<ProductRequest> productRequests = new ArrayList<>(DatabaseHandler.selectFromProductRequest());
        for (ProductRequest productRequest : productRequests) {
            getAllRequests().put(productRequest.getRequestId(), productRequest);
        }
    }
}
