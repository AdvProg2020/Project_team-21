package View.ProductPageUI;

import Controller.Control;
import Model.Account.Account;
import Model.Product;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class ProductAddToCartUI extends UI {
    private static ProductAddToCartUI instance;
    private ProductAddToCartUI()
    {

    }

    public static ProductAddToCartUI getInstance()
    {
        if(instance == null)
            instance = new ProductAddToCartUI();
        return instance;
    }
    @Override
    public void run()
    {
        String productID = ProductMainUI.getInstance().getProductID();
        Product product = Product.getAllProducts().get(productID);
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Enter seller's username: ");
        String sellerUsername = scanner.nextLine();
        System.out.println("Enter quantity: ");
        String quantity = scanner.nextLine();
        try
        {
            Control.getInstance().addToCart(product,sellerUsername,quantity);
            System.out.println("Product " + product.getName() + " has been add to your cart.");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help()
    {

    }

}
