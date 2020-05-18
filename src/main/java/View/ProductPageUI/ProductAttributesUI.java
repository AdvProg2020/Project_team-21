package View.ProductPageUI;

import Model.Product;
import View.UI;

public class ProductAttributesUI extends UI {
    private static ProductAttributesUI instance;
    private ProductAttributesUI()
    {

    }

    public static ProductAttributesUI getInstance()
    {
        if(instance == null)
            instance = new ProductAttributesUI();
        return instance;
    }
    @Override
    public void run()
    {
        String productID = ProductMainUI.getInstance().getProductID();
        Product product = Product.getAllProducts().get(productID);
    }

    @Override
    public void help() {

    }

}
