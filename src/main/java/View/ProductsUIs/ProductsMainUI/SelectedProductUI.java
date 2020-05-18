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

    private String productId;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public void run()  {
//        System.out.println(Product.getProductById(productId));
    }

    @Override
    public void help() {
    }
}
