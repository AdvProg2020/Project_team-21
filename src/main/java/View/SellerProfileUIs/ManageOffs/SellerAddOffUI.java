package View.SellerProfileUIs.ManageOffs;

import Controller.Control;
import Controller.ControlSeller;
import Model.Account.Seller;
import Model.Product;
import View.ConsoleView;
import View.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class SellerAddOffUI extends UI {
    private static SellerAddOffUI instance;
    private SellerAddOffUI()
    {

    }

    public static SellerAddOffUI getInstance()
    {
        if(instance == null)
            instance = new SellerAddOffUI();
        return instance;
    }
    @Override
    public void run()
    {
        Scanner scanner = ConsoleView.getScanner();
        Seller seller = (Seller) Control.getInstance().getUser();
        System.out.println("Enter product IDs you want to add and when finished type [end]: ");
        String productID = "";
        ArrayList<Product> products = new ArrayList<>();
        while(!productID.equalsIgnoreCase("end"))
        {
            productID = scanner.nextLine();
            if (!productID.equalsIgnoreCase("end"))
            {
                if (!ControlSeller.getInstance().checkProductExists(productID) || !ControlSeller.getInstance().checkSellerGotProduct(productID, seller))
                {
                    System.out.println("You don't have this product!");
                    continue;
                }
                products.add(Product.getAllProducts().get(productID));
            }
        }
        System.out.println("Enter off amount: ");
        String amount = scanner.nextLine();
        System.out.println("Enter start time with format [year month day hour minute]");
        String start = scanner.nextLine();
        System.out.println("Enter end time with format [year month day hour minute]");
        String end = scanner.nextLine();
        try
        {
            String reqID = ControlSeller.getInstance().sendAddOfRequest(products,amount,start,end);
            System.out.println("Your request with ID " + reqID + " has been sent!");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

}
