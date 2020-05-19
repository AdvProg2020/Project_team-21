package View.ProductPageUI;

import Controller.Control;
import Model.Product;
import View.ConsoleView;
import View.UI;

public class ProductCompareUI extends UI {
    private static ProductCompareUI instance;
    private ProductCompareUI()
    {

    }

    public static ProductCompareUI getInstance()
    {
        if(instance == null)
            instance = new ProductCompareUI();
        return instance;
    }
    String compareProductID;

    public void setCompareProductID(String compareProductID) {
        this.compareProductID = compareProductID;
    }

    @Override
    public void run()
    {
        String productID = ProductMainUI.getInstance().getProductID();
        Product product = Product.getAllProducts().get(productID);
        Product compareProduct = null;
        if(!Product.getAllProducts().containsKey(compareProductID))
        {
            ConsoleView.getInstance().errorInput("This product doesn't exist.", ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            compareProduct = Product.getAllProducts().get(compareProductID);
            System.out.println("                 Product1                Product2");
            System.out.println("Name:            " +product.getName() + "   " + compareProduct.getName() );
            System.out.println("Price:           " + product.getPrice()+ "   " + compareProduct.getPrice());
            System.out.println("Category:        "+product.getCategory().getName()+"   "+ compareProduct.getCategory().getName());
            System.out.println("Sellers:         "+product.getSellers() + "    "+compareProduct.getSellers());
            System.out.println("Off:             "+Control.getInstance().offOutput(product.getOff())+"    "+Control.getInstance().offOutput(compareProduct.getOff()));
            System.out.println("Average score:   "+product.getBuyersAverageScore()+"   "+compareProduct.getBuyersAverageScore());
            System.out.println("Company:         "+product.getCompany().getName()+"     "+compareProduct.getCompany().getName());
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }


}
