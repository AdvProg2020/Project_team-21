package View.ManagerProfileUIs.ManageDiscountCodes;

import Controller.Control;
import Controller.ControlManager;
import View.ConsoleView;
import View.UI;

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
        try
        {
            ControlManager.getInstance().createDiscountCode(discountID,startDate,endDate,percentage,maxDiscount,maxNumber);
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
