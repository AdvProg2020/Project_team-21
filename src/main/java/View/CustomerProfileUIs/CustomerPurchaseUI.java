package View.CustomerProfileUIs;

import Controller.Control;
import Controller.ControlCustomer;
import Model.Account.Customer;
import View.ConsoleView;
import View.MainMenuUI;
import View.UI;

import java.util.Scanner;

public class CustomerPurchaseUI extends UI {
    private static CustomerPurchaseUI instance;
    private CustomerPurchaseUI()
    {

    }

    public static CustomerPurchaseUI getInstance()
    {
        if(instance == null)
            instance = new CustomerPurchaseUI();
        return instance;
    }
    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Enter receiver's info: ");
        System.out.println("   Name: ");
        String receiverName = scanner.nextLine();
        System.out.println("   Address: ");
        String receiverAddress = scanner.nextLine();
        System.out.println("   Postal code: ");
        String receiverPostalCode = scanner.nextLine();
        System.out.println("   Phone number: ");
        String receiverPhoneNo = scanner.nextLine();
        System.out.println("Enter discount code or type [next]: ");
        String discountCode = scanner.nextLine();
        try
        {
            String logID = ControlCustomer.getInstance().purchase(customer,receiverName,receiverAddress,receiverPhoneNo,receiverPostalCode,discountCode);
            System.out.println("Your purchase has been finalized successfully with logID: " + logID);
            System.out.println("Your new balance is: " + customer.getBalance());
        }
        catch (Exception e)
        {
            System.out.println("Sorry your purchase didn't get complete with error: " + e.getMessage());
        }
        finally {
           ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }

}
