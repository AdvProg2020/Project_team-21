package View.ProductsUIs.SortingUI;

import Controller.Control;
import View.UI;

public class CurrentSortUI extends UI {
    private static CurrentSortUI instance;


    private CurrentSortUI()
    {

    }

    public static CurrentSortUI getInstance() {
        if(instance == null)
            instance = new CurrentSortUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().showCurrentProductSort());
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}


