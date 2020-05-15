package View;

import Controller.ControlManager;
import Model.DiscountCode;

public class ManagerViewDiscountCodeUI extends UI {
    private static ManagerViewDiscountCodeUI instance;
    private ManagerViewDiscountCodeUI()
    {

    }

    public static ManagerViewDiscountCodeUI getInstance()
    {
        if(instance == null)
            instance = new ManagerViewDiscountCodeUI();
        return instance;
    }
    String code = "";

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void run() {
        if(ControlManager.getInstance().checkDiscountCodeExistance(code))
        {
            System.out.println("Percentage: " + DiscountCode.getAllDiscountCodes().get(code).getDiscountPercentage());
            System.out.println("Start time: " + DiscountCode.getAllDiscountCodes().get(code).getStartTime());
            System.out.println("End time: " + DiscountCode.getAllDiscountCodes().get(code).getEndTime());
            System.out.println("Maximum discount amount: " + DiscountCode.getAllDiscountCodes().get(code).getMaxDiscountAmount());
            System.out.println("Maximum for each user: " + DiscountCode.getAllDiscountCodes().get(code).getDiscountNumberForEachUser());
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            ConsoleView.getInstance().errorInput("Code doesn't exist!" , ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }
}
