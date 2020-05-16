package View.ManagerProfileUIs.ManageCategories;

import View.UI;

public class ManagerRemoveProductFromCategoryUI extends UI {
    private static ManagerRemoveProductFromCategoryUI instance;
    private ManagerRemoveProductFromCategoryUI()
    {

    }

    public static ManagerRemoveProductFromCategoryUI getInstance()
    {
        if(instance == null)
            instance = new ManagerRemoveProductFromCategoryUI();
        return instance;
    }
    @Override
    public void run() {

    }

    @Override
    public void help() {

    }

}
