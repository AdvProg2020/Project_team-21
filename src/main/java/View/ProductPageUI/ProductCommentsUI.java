package View.ProductPageUI;

import Controller.Control;
import Model.Account.Account;
import Model.Product;
import Model.Review;
import View.UI;

public class ProductCommentsUI extends UI {
    private static ProductCommentsUI instance;
    private ProductCommentsUI()
    {

    }

    public static ProductCommentsUI getInstance()
    {
        if(instance == null)
            instance = new ProductCommentsUI();
        return instance;
    }
    @Override
    public void run()
    {
        String productID = ProductMainUI.getInstance().getProductID();
        Product product = Product.getAllProducts().get(productID);
        System.out.println("Comments on product " + product.getName()+":");
        System.out.println("[NOTE: If user bought the product you will see a * after their comment]");
        for (Review review : product.getReviewsList())
        {
            System.out.println(review.getUser().getUsername()+":   " + review.getReviewText());
            if(review.hasBought())
                System.out.print("      *");
        }
    }

    @Override
    public void help()
    {
        System.out.println("To add a comment on this product: add comment");
    }

}
