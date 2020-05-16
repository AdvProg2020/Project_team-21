package View.ManagerProfileUIs.ManageDiscountCodes;

import Controller.ControlManager;
import Model.DiscountCode;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class ManagerEditDiscountCodeUI extends UI {
    private static ManagerEditDiscountCodeUI instance;
    private ManagerEditDiscountCodeUI()
    {

    }

    public static ManagerEditDiscountCodeUI getInstance()
    {
        if(instance == null)
            instance = new ManagerEditDiscountCodeUI();
        return instance;
    }

    String id = "";

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        if(!DiscountCode.getAllDiscountCodes().containsKey(id))
        {
            ConsoleView.getInstance().errorInput("This code doesn't exist!" , ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            System.out.println("Say what field you want to edit: ");
            Scanner scanner = ConsoleView.getScanner();
            String field = scanner.nextLine();
            if(!ControlManager.getInstance().editDiscountCodeValidField(field))
            {
                ConsoleView.getInstance().errorInput("This field doesn't exist!", ConsoleView.getInstance().getLastMenu());
            }
            else
            {
                String value = "";
                if(field.matches("(?i)start\\s*time"))
                {
                    System.out.println("Set your new start time in [year month day hour minute] format: ");
                    value = scanner.nextLine();
                    field = "Start date";
                }
                else if(field.matches("(?i)end\\s*time"))
                {
                    System.out.println("Set your new end time in [year month day hour minute] format: ");
                    value = scanner.nextLine();
                    field = "End date";
                }
                else if(field.matches("(?i)percentage|discount\\*percentage"))
                {
                    System.out.println("Set your new discount percentage: ");
                    value = scanner.nextLine();
                    field = "Percentage";
                }
                else if(field.matches("(?i)max\\s*discount"))
                {
                    System.out.println("Set your new max amount for discount: ");
                    value = scanner.nextLine();
                    field = "Max amount";
                }
                else if(field.matches("(?i)max\\s*for\\s*each"))
                {
                    System.out.println("Set your new max times an account can use this: ");
                    value = scanner.nextLine();
                    field ="Max times";
                }
                try {
                    ControlManager.getInstance().editDiscountCode(field,value, id);
                    System.out.println(field + " has been successfully changed to " + value + " for " + id);
                    ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
                }catch (Exception e)
                {
                    ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
                }
            }
        }
    }
    @Override
    public void help() {

    }
}
