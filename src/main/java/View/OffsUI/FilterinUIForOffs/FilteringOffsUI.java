package View.OffsUI.FilterinUIForOffs;

import View.UI;

public class FilteringOffsUI extends UI {
    private static FilteringOffsUI instance;

    private FilteringOffsUI()
    {

    }
    public static FilteringOffsUI getInstance() {
        if(instance == null)
            instance = new FilteringOffsUI();
        return instance;
    }

    @Override
    public void run() {

    }

    @Override
    public void help() {
        System.out.println("show available filters");
        System.out.println("filter [an available filter] [filterInput/filterType] ");
        System.out.println("current filters");
        System.out.println("disable filter [a selected filter]");
    }

    @Override
    public void sort() {

    }
}
