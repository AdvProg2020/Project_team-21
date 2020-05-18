package View.CustomerProfileUIs;

import Controller.Control;
import Model.Account.Customer;
import View.ConsoleView;
import View.UI;

public class CustomerViewBalanceUI extends UI {
    private static CustomerViewBalanceUI instance;
    private CustomerViewBalanceUI()
    {

    }

    public static CustomerViewBalanceUI getInstance()
    {
        if(instance == null)
            instance = new CustomerViewBalanceUI();
        return instance;
    }
    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        System.out.println("Your balance is: " + customer.getBalance());
        ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
    }

    @Override
    public void help() {

    }

}
