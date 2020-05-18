package View.ProductPageUI;

import Controller.Control;
import Model.Product;
import View.ConsoleView;
import View.UI;

public class ProductMainUI extends UI {
    private static ProductMainUI instance;
    private ProductMainUI()
    {

    }

    public static ProductMainUI getInstance()
    {
        if(instance == null)
            instance = new ProductMainUI();
        return instance;
    }
    String productID;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    @Override
    public void run()
    {
        if(!Product.getAllProducts().containsKey(productID))
        {
            ConsoleView.getInstance().errorInput("This product doesn't exist!", ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            Product product = Product.getAllProducts().get(productID);
            System.out.println("Welcome to product page for " +product.getName());
        }
    }

    @Override
    public void help()
    {
        System.out.println("To view infos about this product: digest");
        System.out.println("To view full infos of this product: attributes");
        System.out.println("To compare this product to another: compare [productID]");
        System.out.println("To see scores and comments and submit a comment: comments");
    }

}
