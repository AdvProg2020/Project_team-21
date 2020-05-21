package View.ManagerProfileUIs.ManageCategories;

import Controller.Control;
import Controller.ControlManager;
import Model.Category;
import View.ConsoleView;
import View.UI;

public class ManagerEditCategoryNameUI extends UI {
    private static ManagerEditCategoryNameUI instance;
    private ManagerEditCategoryNameUI()
    {

    }

    public static ManagerEditCategoryNameUI getInstance()
    {
        if(instance == null)
            instance = new ManagerEditCategoryNameUI();
        return instance;
    }
    @Override
    public void run()
    {
        System.out.println("Enter your new name: ");
        String newName = ConsoleView.getScanner().nextLine();
        try
        {
            ControlManager.getInstance().changeCategoryName(newName);
            ManagerEditCategoryUI.getInstance().setCategoryName(newName);
            System.out.println("Your category's name has been successfully changed to " + newName);
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }catch(Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage() , ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }

}
