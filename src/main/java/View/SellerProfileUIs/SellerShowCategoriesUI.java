package View.SellerProfileUIs;

import Model.Category;
import View.UI;

public class SellerShowCategoriesUI extends UI {
    private static SellerShowCategoriesUI instance;
    private SellerShowCategoriesUI()
    {

    }

    public static SellerShowCategoriesUI getInstance()
    {
        if(instance == null)
            instance = new SellerShowCategoriesUI();
        return instance;
    }
    @Override
    public void run()
    {
        int i = 1;
        for (Category category : Category.getAllCategories()) {
            System.out.println(i + " " + category.getName());
            i++;
        }
    }

    @Override
    public void help() {

    }

}
