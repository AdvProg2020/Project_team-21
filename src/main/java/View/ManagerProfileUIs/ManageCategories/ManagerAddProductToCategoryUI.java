package View.ManagerProfileUIs.ManageCategories;

import View.UI;

public class ManagerAddProductToCategoryUI extends UI {
    private static ManagerAddProductToCategoryUI instance;
    private ManagerAddProductToCategoryUI()
    {

    }

    public static ManagerAddProductToCategoryUI getInstance()
    {
        if(instance == null)
            instance = new ManagerAddProductToCategoryUI();
        return instance;
    }
    @Override
    public void run() {

    }

    @Override
    public void help() {

    }

}
