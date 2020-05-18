package View.CustomerProfileUIs.CustomerCartUIs;

import Controller.Control;
import Model.Account.Customer;
import Model.Product;
import Model.ShoppingCart;
import View.ConsoleView;
import View.UI;

public class CustomerCartShowProductsUI extends UI
{
    private static CustomerCartShowProductsUI instance;
    private CustomerCartShowProductsUI()
    {

    }

    public static CustomerCartShowProductsUI getInstance()
    {
        if(instance == null)
            instance = new CustomerCartShowProductsUI();
        return instance;
    }
    @Override
    public void run()
    {
        ShoppingCart cart = ((Customer) Control.getInstance().getUser()).getShoppingCart();
        System.out.println("Product Name(ID)                     Quantity                Price per          Total Price");
        for (Product product : cart.getProductsQuantity().keySet()) {
            System.out.println(product.getName() + "(" + product.getProductId()+")" + "          " + cart.getProductsQuantity().get(product) + "           " + product.getPrice() + "        " + product.getPrice()*cart.getProductsQuantity().get(product));
        }
        ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
    }

    @Override
    public void help() {

    }


}
