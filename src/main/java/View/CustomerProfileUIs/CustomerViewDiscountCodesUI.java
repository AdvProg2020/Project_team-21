package View.CustomerProfileUIs;

import Controller.Control;
import Model.Account.Customer;
import View.UI;

public class CustomerViewDiscountCodesUI extends UI {
    private static CustomerViewDiscountCodesUI instance;
    private CustomerViewDiscountCodesUI()
    {

    }

    public static CustomerViewDiscountCodesUI getInstance()
    {
        if(instance == null)
            instance = new CustomerViewDiscountCodesUI();
        return instance;
    }
    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        System.out.println("Your discount codes: ");
        int i = 1;
        for (String s : customer.getDiscountCodes().keySet())
        {
            System.out.println(i+ ". " + s);
            i++;
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }

}
