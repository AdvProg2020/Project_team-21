package View.OffsUI.FilterinUIForOffs;

import Controller.Control;
import View.UI;

public class DisableFilterOffsUI extends UI {
    private static DisableFilterOffsUI instance;
    private static DisableFilterOffsUI disableFilterUI;
    private String categoryName="Main";

    private DisableFilterOffsUI()
    {

    }
    public static DisableFilterOffsUI getInstance() {
        if(instance == null)
            instance = new DisableFilterOffsUI();
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



