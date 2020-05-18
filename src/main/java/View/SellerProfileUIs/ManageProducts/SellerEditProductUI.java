package View.SellerProfileUIs.ManageProducts;

import Controller.ControlSeller;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class SellerEditProductUI extends UI {
    private static SellerEditProductUI instance;
    private SellerEditProductUI()
    {

    }

    public static SellerEditProductUI getInstance()
    {
        if(instance == null)
            instance = new SellerEditProductUI();
        return instance;
    }
    String productID;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public void run()
    {
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Enter the field you want to edit [name/price]:");
        String field  = scanner.nextLine();
        System.out.println("Enter its new value: ");
        String value = scanner.nextLine();
        try
        {
            String reqID = ControlSeller.getInstance().sendProductEditReq(productID,field,value);
            System.out.println("Your request with request ID " + reqID + " has been sent!");
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
