package View.ProductsUIs.ProductsMainUI;

        import Controller.Control;
        import View.UI;
        import Model.Product;

public class SelectedProductUI extends UI {
    private static SelectedProductUI instance;

    private SelectedProductUI()
    {

    }
    public static SelectedProductUI getInstance() {
        if(instance == null)
            instance = new SelectedProductUI();
        return instance;
    }

    @Override
    public void run()  {

    }

    @Override
    public void help() {
    }
}
