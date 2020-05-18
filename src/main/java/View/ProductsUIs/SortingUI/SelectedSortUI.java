package View.ProductsUIs.SortingUI;

import Controller.Control;
import View.UI;

public class SelectedSortUI extends UI {
    private static SelectedSortUI instance;


    private SelectedSortUI()
    {

    }

    public static SelectedSortUI getInstance() {
        if(instance == null)
            instance = new SelectedSortUI();
        return instance;
    }

    @Override
    public void run() {
        //TODO
//        System.out.println(Control.getInstance().makeSort());
    }

    @Override
    public void help() {

    }
}

