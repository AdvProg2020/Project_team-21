package Model.Request;

import Model.Category;
import Model.Company;
import Model.Off;
import Model.Product;

public class ProductRequest extends Request {
    public ProductRequest(String requestId,String productId, String name, Company company, double price, Category category)
    {
        Product product = new Product(productId,name,company,price,category);
         requestedProducts.put(requestId,product);
         Request.addRequest(requestId,this);

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
}
