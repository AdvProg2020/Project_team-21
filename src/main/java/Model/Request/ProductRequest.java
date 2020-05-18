package Model.Request;

import Model.Account.Seller;
import Model.Category;
import Model.Company;
import Model.Product;

public class ProductRequest extends Request {
    Seller provider;
    public ProductRequest(String requestId, String productId, String name, Company company, double price, Category category, Seller provider)
    {
        Product product = new Product(productId,name,company,price,category);
         requestedProducts.put(requestId,product);
         Request.addRequest(requestId,this);
         this.provider = provider;
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
        Product.addProduct(Request.getRequestedProducts().get(requestId));
        declineReq(requestId);
    }

    @Override
    public String getType() {
        return "Product Request";
    }
}
