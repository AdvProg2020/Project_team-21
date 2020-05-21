package View.SellerProfileUIs.ManageProducts;

import Controller.Control;
import Model.Account.Seller;
import Model.Product;
import View.ConsoleView;
import View.UI;

public class SellerViewProductUI extends UI {
    private static SellerViewProductUI instance;
    private SellerViewProductUI()
    {

    }

    public static SellerViewProductUI getInstance()
    {
        if(instance == null)
            instance = new SellerViewProductUI();
        return instance;
    }
    String productID;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public void run()
    {
        Seller seller = (Seller) Control.getInstance().getUser();
        Product product;
        if(Product.getAllProducts().containsKey(productID))
        {
            product = Product.getAllProducts().get(productID);
            if(seller.getAllProducts().contains(product))
            {
                System.out.println("Name: " + product.getName());
                System.out.println("Company name: " + product.getCompany().getName());
                System.out.println("Category: " + product.getCategory().getName());
                System.out.println("Price: " + product.getPrice());
                ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
            }
            else
            {
                ConsoleView.getInstance().errorInput("You don't have this product!" , ConsoleView.getInstance().getLastMenu());
            }
        }
        else
        {
            ConsoleView.getInstance().errorInput("This product doesn't exist!" , ConsoleView.getInstance().getLastMenu());
        }


    }

    @Override
    public void help()
    {

    }

    @Override
    public void sort() {

    }

}
