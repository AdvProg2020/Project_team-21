package View.ManagerProfileUIs.ManageCategories;

import Controller.ControlManager;
import View.ConsoleView;
import View.UI;

public class ManagerEditCategoryUI extends UI {
    private static ManagerEditCategoryUI instance;
    private ManagerEditCategoryUI()
    {

    }

    public static ManagerEditCategoryUI getInstance()
    {
        if(instance == null)
            instance = new ManagerEditCategoryUI();
        return instance;
    }
    String categoryName;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public void run()
    {
        if(!ControlManager.getInstance().checkCategoryExistance(categoryName))
        {
            ConsoleView.getInstance().errorInput("This category doesn't exist!",ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help()
    {
        System.out.println("If you want to change it's name type [name]\nIf you want to delete or add a product to it type [add/remove productID]");
    }
}
