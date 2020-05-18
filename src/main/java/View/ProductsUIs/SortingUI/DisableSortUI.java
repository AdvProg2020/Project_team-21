package View.ProductsUIs.SortingUI;

import Controller.Control;
import View.UI;

public class DisableSortUI extends UI {
    private static DisableSortUI instance;


    private DisableSortUI()
    {

    }

    public static DisableSortUI getInstance() {
        if(instance == null)
            instance = new DisableSortUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().disableSort() + "\n");
    }

    @Override
    public void help() {

    }
}
