package View.ProductsUIs.FilteringUI;

import Controller.Control;
import View.UI;

public class AvailableFilterUI extends UI {
    private static AvailableFilterUI instance;
    private static AvailableFilterUI availableFilterUI;
    private String categoryName="Main";

    private AvailableFilterUI()
    {

    }
    public static AvailableFilterUI getInstance() {
        if(instance == null)
            instance = new AvailableFilterUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println("Available filters are: ");
        System.out.println(Control.getInstance().showAvailableFilters());
    }

    @Override
    public void help() {
    }
}

