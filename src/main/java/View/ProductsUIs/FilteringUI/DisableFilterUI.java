package View.ProductsUIs.FilteringUI;

import Controller.Control;
import View.UI;

public class DisableFilterUI extends UI {
    private static DisableFilterUI instance;
    private static DisableFilterUI disableFilterUI;
    private String categoryName="Main";

    private DisableFilterUI()
    {

    }
    public static DisableFilterUI getInstance() {
        if(instance == null)
            instance = new DisableFilterUI();
        return instance;
    }

    private String disablingFilter;

    public void setDisablingFilter(String disablingFilter) {
        this.disablingFilter = disablingFilter;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().disableFilter(disablingFilter));
    }

    @Override
    public void help() {

    }
}



