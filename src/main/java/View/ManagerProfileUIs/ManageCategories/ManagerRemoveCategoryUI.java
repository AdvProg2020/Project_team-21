package View.ManagerProfileUIs.ManageCategories;

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
    @Override
    public void run() {

    }

    @Override
    public void help() {

    }
}
