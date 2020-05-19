package View.ProductsUIs.SortingUI;

import Controller.Control;
import View.UI;

public class AvailableSortUI extends UI {
    private static AvailableSortUI instance;


    private AvailableSortUI()
    {

    }

    public static AvailableSortUI getInstance() {
        if(instance == null)
            instance = new AvailableSortUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().showAvailableSorts("product"));
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}

