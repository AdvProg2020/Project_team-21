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

    private String sortType;

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().makeSort(sortType,"product"));
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}

