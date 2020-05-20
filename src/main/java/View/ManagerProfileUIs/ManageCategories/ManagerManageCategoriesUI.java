package View.ManagerProfileUIs.ManageCategories;

import Model.Category;
import View.ManagerProfileUIs.ManageDiscountCodes.ManagerRemoveDiscountCodeUI;
import View.UI;

public class ManagerManageCategoriesUI extends UI {
    private static ManagerManageCategoriesUI instance;
    private ManagerManageCategoriesUI()
    {

    }

    public static ManagerManageCategoriesUI getInstance()
    {
        if(instance == null)
            instance = new ManagerManageCategoriesUI();
        return instance;
    }

    @Override
    public void run()
    {
        for (Category category : Category.getAllCategories())
        {
            System.out.println(category.getName());
        }
    }
    public String categoriesTest()
    {
        String categories = "";
        for (Category category : Category.getAllCategories()) {
            categories += category.getName();
        }
        return categories;
    }
    @Override
    public void help()
    {
        System.out.println("To add a category: add [category]");
        System.out.println("To edit a category: edit [category]");
        System.out.println("To remove a category : remove [category]");
    }

    @Override
    public void sort() {

    }
}
