package View.ManagerProfileUIs.ManageProducts;

import Controller.ControlManager;
import View.ConsoleView;
import View.UI;

public class ManagerRemoveProductUI extends UI {
    private static ManagerRemoveProductUI instance;
    private ManagerRemoveProductUI()
    {

    }

    public static ManagerRemoveProductUI getInstance() {
        if (instance == null)
            instance = new ManagerRemoveProductUI();
        return instance;
    }
    private String ID;

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public void run() {
        try {
            ControlManager.getInstance().removeProduct(ID);
            System.out.println("Product " + ID + "has been successfully deleted!");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}
