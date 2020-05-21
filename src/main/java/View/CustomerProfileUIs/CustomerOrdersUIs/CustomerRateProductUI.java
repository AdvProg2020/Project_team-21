package View.CustomerProfileUIs.CustomerOrdersUIs;

import Controller.Control;
import Controller.ControlCustomer;
import Model.Account.Customer;
import View.ConsoleView;
import View.UI;

public class CustomerRateProductUI extends UI {
    private static CustomerRateProductUI instance;
    private CustomerRateProductUI()
    {

    }

    public static CustomerRateProductUI getInstance()
    {
        if(instance == null)
            instance = new CustomerRateProductUI();
        return instance;
    }
    String score;
    String productID;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        try
        {
            ControlCustomer.getInstance().rateProduct(productID,score,customer);
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
