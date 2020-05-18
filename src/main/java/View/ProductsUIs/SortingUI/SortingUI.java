package View.ProductsUIs.SortingUI;

import View.UI;

public class SortingUI extends UI {
    private static SortingUI instance;
    private static SortingUI sortingUI;
    private String categoryName="Main";

    private SortingUI()
    {

    }

    public static SortingUI getInstance() {
        if(instance == null)
            instance = new SortingUI();
        return instance;
    }

    @Override
    public void run() {

    }

    @Override
    public void help() {
        System.out.println("show available sort");
        System.out.println("sort [an available sort]");
        System.out.println("current sort");
        System.out.println("disable sort");
    }
}
