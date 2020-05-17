package Model.Request;

import Model.Account.Seller;
import Model.Category;
import Model.Company;
import Model.Product;

public class ProductRequest extends Request {
    private Seller provider;
    private String editField;
    private String newValueEdit;
    private Seller seller;
    public ProductRequest(String requestId, String productId, String name, Company company, double price, Category category, Seller provider, RequestType requestType, Seller seller, Product product)
    {
        super(requestType);
        if(requestType.equals(RequestType.ADD))
             product = new Product(productId,name,company,price,category,seller);
         requestedProducts.put(requestId,product);
         Request.addRequest(requestId,this);
         this.provider = provider;
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
            Product.addProduct(product);
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
            product.addSeller(seller);
        declineReq(requestId);
    }
}
