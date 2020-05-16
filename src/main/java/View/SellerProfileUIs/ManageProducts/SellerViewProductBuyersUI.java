package View.SellerProfileUIs.ManageProducts;

import Controller.Control;
import Controller.ControlSeller;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.Product;
import View.ConsoleView;
import View.UI;

import java.util.ServiceConfigurationError;

public class SellerViewProductBuyersUI extends UI {
    private static SellerViewProductBuyersUI instance;
    private SellerViewProductBuyersUI()
    {

    }

    public static SellerViewProductBuyersUI getInstance()
    {
        if(instance == null)
            instance = new SellerViewProductBuyersUI();
        return instance;
    }
    String productID;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public void run()
    {
        Seller user = (Seller) Control.getInstance().getUser();
        if(ControlSeller.getInstance().checkProductExists(productID))
        {
            Product product = Product.getAllProducts().get(productID);
            if(ControlSeller.getInstance().checkSellerGotProduct(productID,user))
            {
                for (Customer buyer : product.getBuyers())
                {
                    System.out.println("Username: " + buyer.getUsername() + "Name: " + buyer.getFirstName()+" " + buyer.getLastName());
                }
                ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
            }
            else
            {
                ConsoleView.getInstance().errorInput("You don't have this product!" , ConsoleView.getInstance().getLastMenu());
            }
        }
        else
        {
            ConsoleView.getInstance().errorInput("This product doesn't exist!", ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

}
