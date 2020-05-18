package View.ProductsUIs.FilteringUI;

import View.UI;

public class FilteringUI extends UI {
    private static FilteringUI instance;
    private static FilteringUI productsMainUI;
    private String categoryName="Main";

    private FilteringUI()
    {

    }
    public static FilteringUI getInstance() {
        if(instance == null)
            instance = new FilteringUI();
        return instance;
    }

    @Override
    public void run() {

    }

    @Override
    public void help() {
        System.out.println("show available filters");
        System.out.println("filter [an available filter] [filterInput/filterType] ");// filter salam khodafez
        System.out.println("current filters");
        System.out.println("disable filter [a selected filter]");
    }
}
