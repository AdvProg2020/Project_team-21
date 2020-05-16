package View.ManagerProfileUIs.ManageDiscountCodes;

import Controller.ControlManager;
import View.ConsoleView;
import View.UI;

public class ManagerRemoveDiscountCodeUI extends UI {
    private static ManagerRemoveDiscountCodeUI instance;
    private ManagerRemoveDiscountCodeUI()
    {

    }

    public static ManagerRemoveDiscountCodeUI getInstance()
    {
        if(instance == null)
            instance = new ManagerRemoveDiscountCodeUI();
        return instance;
    }
    private String id="";

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void run()
    {
        try
        {
            ControlManager.getInstance().removeDiscountCode(id);
            System.out.println("Discount code " + id  +"has successfully deleted!");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }
}
