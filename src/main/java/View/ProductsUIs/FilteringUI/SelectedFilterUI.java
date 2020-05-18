package View.ProductsUIs.FilteringUI;

import Controller.Control;
import View.UI;

public class SelectedFilterUI extends UI {
    private static SelectedFilterUI instance;

    private SelectedFilterUI()
    {

    }
    public static SelectedFilterUI getInstance() {
        if(instance == null)
            instance = new SelectedFilterUI();
        return instance;
    }

    private String filterInput;
    private String filterType;

    public void setFilterInput(String filterInput) {
        this.filterInput = filterInput;
    }
    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().createFilter(filterType,filterInput));
    }

    @Override
    public void help() {
        System.out.println("Enter desired value for your selected filter\n(If you chose price, you can " +"\n" +
                "separate start and end of price range with space,comma or dash)");
    }
}




