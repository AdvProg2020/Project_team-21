package View.CustomerProfileUIs.CustomerCartUIs;

import Controller.Control;
import Controller.ControlCustomer;
import Model.Account.Customer;
import View.ConsoleView;
import View.UI;

public class CustomerIncreaseDecreaseProductCartUI extends UI {
    private static CustomerIncreaseDecreaseProductCartUI instance;
    private CustomerIncreaseDecreaseProductCartUI()
    {

    }

    public static CustomerIncreaseDecreaseProductCartUI getInstance()
    {
        if(instance == null)
            instance = new CustomerIncreaseDecreaseProductCartUI();
        return instance;
    }
    boolean increase;
    String productID;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
    }
    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        try
        {
            ControlCustomer.getInstance().increaseOrDecreaseProductInCart(productID,customer,increase);
            if(increase)
                System.out.println("Product " + productID + " has been increased!");
            else
                System.out.println("Product " + productID + " has been decreased!");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }

}
