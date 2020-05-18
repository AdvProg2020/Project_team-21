package View.ManagerProfileUIs.ManageDiscountCodes;

import Controller.Control;
import Controller.ControlManager;
import Model.Account.Account;
import Model.Account.Customer;
import View.ConsoleView;
import View.UI;

import java.util.HashMap;
import java.util.Scanner;

public class ManagerCreateDiscountCodeUI extends UI {
    private static ManagerCreateDiscountCodeUI instance;
    private ManagerCreateDiscountCodeUI()
    {

    }

    public static ManagerCreateDiscountCodeUI getInstance() {
        if (instance == null)
            instance = new ManagerCreateDiscountCodeUI();
        return instance;
    }

    @Override
    public void run()
    {
        Scanner scanner = ConsoleView.getScanner();
        String discountID = Control.getInstance().randomString(10);
        System.out.println("Enter start date in [year month day hour minute]: ");
        String startDate = scanner.nextLine();
        System.out.println("Enter end date in [year month day hour minute]: ");
        String endDate = scanner.nextLine();
        System.out.println("Enter percentage discount: ");
        String percentage =scanner.nextLine();
        System.out.println("Enter maximum amount of discount you want to give: ");
        String maxDiscount =scanner.nextLine();
        System.out.println("Enter allowed number of times a user can use this code: ");
        String maxNumber = scanner.nextLine();
        System.out.println("Enter customers' UserNames that you want to have this code and when ended type [end] :");
        HashMap<String,Customer> codeOwners = new HashMap<>();
        String username = "";
        while (!username.equalsIgnoreCase("end"))
        {
            username = scanner.nextLine();
            if(!username.equalsIgnoreCase("end"))
            {
                if(!Account.getAllAccounts().containsKey(username))
                {
                    System.out.println("This username doesn't exist!");
                    continue;
                }
                if(!(Account.getAllAccounts().get(username) instanceof Customer))
                {
                    System.out.println("This username is not a customer.");
                    continue;
                }
                codeOwners.put(username,(Customer) Account.getAllAccounts().get(username));
            }
        }
        try
        {
            ControlManager.getInstance().createDiscountCode(discountID,startDate,endDate,percentage,maxDiscount,maxNumber,codeOwners);
            System.out.println("Discount code with " + discountID + "has been successfully made!");
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
