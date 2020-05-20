package View.OffsUI.FilterinUIForOffs;

import Controller.Control;
import View.UI;

public class CurrentFiltersOffsUI extends UI {
    private static CurrentFiltersOffsUI instance;

    private CurrentFiltersOffsUI()
    {

    }
    public static CurrentFiltersOffsUI getInstance() {
        if(instance == null)
            instance = new CurrentFiltersOffsUI();
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


