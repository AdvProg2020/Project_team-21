package Model.Request;

import Model.Account.Seller;
import Model.Category;
import Model.Company;
import Model.Product;
import Model.SaveData;

import java.util.ArrayList;

public class ProductRequest extends Request {
    private Seller provider;
    private String editField;
    private String newValueEdit;
    private Seller seller;
    private String providerUsername;
    public ProductRequest(String requestId, String productId, String name, Company company, double price, Category category, Seller provider, RequestType requestType, Seller seller, Product product)
    {
        super(requestType);
        String imagePath="";
        if(requestType.equals(RequestType.ADD))
             product = new Product(productId,name,company,price,category,seller,imagePath);
         requestedProducts.put(requestId,product);
         Request.addRequest(requestId,this);
         this.provider = provider;
         providerUsername = provider.getUsername();
         setRequestId(requestId);
        SaveData.saveData(this, getRequestId(), SaveData.productReqFile);
        SaveData.saveData(product, (getRequestId()+product.getProductId()), SaveData.productRequestFile);
    }

    public String getProviderUsername() {
        return providerUsername;
    }

    public void setEditField(String editField) {
        this.editField = editField;
    }

    public void setNewValueEdit(String newValueEdit) {
        this.newValueEdit = newValueEdit;
    }

    public Seller getProvider() {
        return provider;
    }

    @Override
    public void declineReq(String requestId)
    {
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
            getRequestedProducts().put(((Product)object).getProductId() ,(Product) (object));
        }

        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.productReqFile)));
        for (Object object : objects2) {
            getAllRequests().put(((ProductRequest)object).getRequestId() ,(ProductRequest) (object));
        }
    }
}
