package View.OffsUI.FilterinUIForOffs;

import Controller.Control;
import View.UI;

public class AvailableFilterOffsUI extends UI {
    private static AvailableFilterOffsUI instance;
    private static AvailableFilterOffsUI availableFilterUI;
    private String categoryName="Main";

    private AvailableFilterOffsUI()
    {

    }
    public static AvailableFilterOffsUI getInstance() {
        if(instance == null)
            instance = new AvailableFilterOffsUI();
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

    @Override
    public void sort() {

    }
}

