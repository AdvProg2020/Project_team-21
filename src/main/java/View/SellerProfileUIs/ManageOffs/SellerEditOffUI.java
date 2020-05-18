package View.SellerProfileUIs.ManageOffs;

import Controller.ControlSeller;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class SellerEditOffUI extends UI {
    private static SellerEditOffUI instance;
    private SellerEditOffUI()
    {

    }

    public static SellerEditOffUI getInstance()
    {
        if(instance == null)
            instance = new SellerEditOffUI();
        return instance;
    }
    String offID;

    public void setOffID(String offID) {
        this.offID = offID;
    }

    @Override
    public void run()
    {
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Type field that you want to edit: [add product/remove product/amount/start/end]");
        String field = scanner.nextLine();
        String value = "";
        String productID = "null";
        if(field.equalsIgnoreCase("add product") || field.equalsIgnoreCase("remove product"))
        {
            System.out.println("Enter product's ID: ");
            productID = scanner.nextLine();
        }
        else
        {
            System.out.println("Enter its new value: ");
            value = scanner.nextLine();
        }
        try
        {
            String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,field,value,productID);
            System.out.println("Your request with ID " + reqID + " has been sent!");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void help()
    {
        System.out.println("To edit any field type one of these phrases:\n  add product\n  remove product\n  amount\n  start\n  end");
    }

}
