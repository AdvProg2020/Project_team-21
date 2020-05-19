package View.ProductsUIs.FilteringUI;

import Controller.Control;
import View.UI;

public class CurrentFiltersUI extends UI {
    private static CurrentFiltersUI instance;

    private CurrentFiltersUI()
    {

    }
    public static CurrentFiltersUI getInstance() {
        if(instance == null)
            instance = new CurrentFiltersUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().showCurrentFilters());
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}


