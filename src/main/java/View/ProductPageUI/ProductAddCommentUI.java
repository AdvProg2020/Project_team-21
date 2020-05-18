package View.ProductPageUI;

import Controller.Control;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Product;
import Model.Review;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class ProductAddCommentUI extends UI {
    private static ProductAddCommentUI instance;
    private ProductAddCommentUI()
    {

    }

    public static ProductAddCommentUI getInstance()
    {
        if(instance == null)
            instance = new ProductAddCommentUI();
        return instance;
    }
    @Override
    public void run()
    {
        Account user = Control.getInstance().getUser();
        if(user == null)
        {
            ConsoleView.getInstance().errorInput("You should first login to comment.",ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            boolean hasBought = false;
            String productID = ProductMainUI.getInstance().getProductID();
            Product product = Product.getAllProducts().get(productID);
            Scanner scanner = ConsoleView.getScanner();
            System.out.println("Title: ");
            String text = "Title: " + scanner.nextLine() + " Content: ";
            System.out.println("Content: ");
            text += scanner.nextLine();
            if(user instanceof Customer)
            {
                Customer customer = (Customer) user;
                if(product.getBuyers().contains(customer))
                    hasBought = true;
            }
            product.addReview(new Review(user,product,text,hasBought));
            System.out.println("Your comment has been added.");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

}
