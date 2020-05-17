package View.SellerProfileUIs;

import Controller.Control;
import Controller.ControlSeller;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class SellerAddProductReqUI extends UI {
    private static SellerAddProductReqUI instance;
    private SellerAddProductReqUI()
    {

    }

    public static SellerAddProductReqUI getInstance()
    {
        if(instance == null)
            instance = new SellerAddProductReqUI();
        return instance;
    }

    @Override
    public void run()
    {
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("1.make a new product\n2.add a new product to your sell list");
        String opt = scanner.nextLine();
        if(opt.equalsIgnoreCase("1"))
        {
            System.out.println("Enter product's name: ");
            String name = scanner.nextLine();
            System.out.println("Enter product's category name: ");
            String categoryName = scanner.nextLine();
            System.out.println("Enter product's price: ");
            String price = scanner.nextLine();
            System.out.println("Enter product's company name: ");
            String companyName = scanner.nextLine();
            System.out.println("Enter company's location: ");
            String companyLocation = scanner.nextLine();
            try
            {
                String reqID = ControlSeller.getInstance().sendAddProductReq(name,companyName,categoryName,price,companyLocation);
                System.out.println("Your request with id " + reqID + " has been sent!");
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        else if(opt.equalsIgnoreCase("2"))
        {
            System.out.println("Enter product's ID: ");
            String productID = scanner.nextLine();
            try
            {
                String reqID = ControlSeller.getInstance().sendAddSellerProductReq(productID);
                System.out.println("Your request with id " + reqID + " has been sent!");
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            System.out.println("Your command is wrong!");
        }

    }

    @Override
    public void help() {

    }
}
