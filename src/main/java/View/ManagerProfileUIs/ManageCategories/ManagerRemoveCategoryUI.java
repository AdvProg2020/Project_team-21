package View.ManagerProfileUIs.ManageCategories;

import Controller.ControlManager;
import Model.Category;
import View.ConsoleView;
import View.UI;

public class ManagerRemoveCategoryUI extends UI {
    private static ManagerRemoveCategoryUI instance;
    private ManagerRemoveCategoryUI()
    {

    }

    public static ManagerRemoveCategoryUI getInstance()
    {
        if(instance == null)
            instance = new ManagerRemoveCategoryUI();
        return instance;
    }
    String categoryName;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void run()
    {
        if(!ControlManager.getInstance().checkCategoryExistance(categoryName))
        {
            ConsoleView.getInstance().errorInput("This category doesn't exist!",ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            System.out.println("Are you sure you want to delete " + categoryName + "? [y/n]");
            String yn = ConsoleView.getScanner().nextLine();
            if(yn.matches("(?i)y|yes"))
            {
                for (Category category : Category.getAllCategories()) {
                    if(category.getName().equalsIgnoreCase(categoryName))
                        Category.removeCategory(category);
                }
                ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
            }
            else
            {
                ConsoleView.getInstance().errorInput("Ok. We don't delete it!" , ConsoleView.getInstance().getLastMenu());
            }
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}
