package View.ProductPageUI;

import Model.Account.Seller;
import Model.Product;
import View.UI;

public class ProductDigestUI extends UI
{
    private static ProductDigestUI instance;
    private ProductDigestUI()
    {

    }

    public static ProductDigestUI getInstance()
    {
        if(instance == null)
            instance = new ProductDigestUI();
        return instance;
    }

    @Override
    public void run()
    {
        String productID = ProductMainUI.getInstance().getProductID();
        Product product = Product.getAllProducts().get(productID);
        System.out.println("Name: " + product.getName());
        System.out.println("ID: " + product.getProductId());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Off: " + product.getOff());
        System.out.println("Category: " + product.getCategory().getName());
        System.out.println("Average score: " + product.getBuyersAverageScore());
        System.out.println("Sellers for this product: ");
        for (Seller seller : product.getSellers()) {
            System.out.println("   Username: " + seller.getUsername() + "    Name:" + seller.getFirstName()+" "+seller.getLastName());
        }
    }

    @Override
    public void help()
    {
        System.out.println("To add this product to your shopping cart: add to cart");
    }

}
