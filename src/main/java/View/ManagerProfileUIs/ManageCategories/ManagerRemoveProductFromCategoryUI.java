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
    String productId;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public void run()
    {

    }

    @Override
    public void help() {

    }

}
