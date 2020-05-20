package View.OffsUI.FilterinUIForOffs;

        import Controller.Control;
        import View.UI;

public class SelectedFilterForOffUI extends UI {
    private static SelectedFilterForOffUI instance;

    private SelectedFilterForOffUI()
    {

    }
    public static SelectedFilterForOffUI getInstance() {
        if(instance == null)
            instance = new SelectedFilterForOffUI();
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
        System.out.println(Control.getInstance().createFilterForOffProducts(filterType,filterInput));
    }

    @Override
    public void help() {
        System.out.println("Enter desired value for your selected filter\n(If you chose price, you can " +"\n" +
                "separate start and end of price range with space,comma or dash)");
    }
}





