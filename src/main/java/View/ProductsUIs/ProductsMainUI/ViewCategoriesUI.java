package View.ProductsUIs.ProductsMainUI;

import View.UI;

import Model.Category;

public class ViewCategoriesUI  extends UI {
    private static ViewCategoriesUI instance;
    private static ViewCategoriesUI productsMainUI;
    private String categoryName="Main";

    private ViewCategoriesUI ()
    {

    }
    public static ViewCategoriesUI getInstance() {
        if(instance == null)
            instance = new ViewCategoriesUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println(Category.getAllCategories());
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}
