package View.CustomerProfileUIs.CustomerCartUIs;

import Controller.Control;
import Model.Account.Customer;
import View.UI;

public class CustomerShowTotalPriceCartUI extends UI {
    private static CustomerShowTotalPriceCartUI instance;
    private CustomerShowTotalPriceCartUI()
    {

    }

    public static CustomerShowTotalPriceCartUI getInstance()
    {
        if(instance == null)
            instance = new CustomerShowTotalPriceCartUI();
        return instance;
    }
    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        System.out.println("Your total price is : " + customer.getShoppingCart().getPrice());
        System.out.println("Albate ghabeli ham nadare.");
    }

    @Override
    public void help() {

    }

}
