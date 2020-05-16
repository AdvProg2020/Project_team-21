package View.ManagerProfileUIs.ManageCategories;

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
    @Override
    public void run() {

    }

    @Override
    public void help() {

    }
}
