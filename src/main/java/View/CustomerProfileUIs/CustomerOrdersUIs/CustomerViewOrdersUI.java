package View.CustomerProfileUIs.CustomerOrdersUIs;

import Controller.Control;
import Model.Account.Customer;
import Model.Log.BuyLog;
import View.UI;

public class CustomerViewOrdersUI extends UI {
    private static CustomerViewOrdersUI instance;
    private CustomerViewOrdersUI()
    {

    }

    public static CustomerViewOrdersUI getInstance()
    {
        if(instance == null)
            instance = new CustomerViewOrdersUI();
        return instance;
    }
    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        int i = 1;
        for (BuyLog buyLog : customer.getBuyLogs())
        {
            System.out.println(i + ". Order ID: " + buyLog.getLogId());
            i++;
        }
    }

    @Override
    public void help()
    {
        System.out.println("To show an order's infos: show order [orderID]");
        System.out.println("To rate a product you have bought: rate [productID] [1-5]");
        System.out.println("To sort orders by productId: sort orders");
    }

    @Override
    public void sort() {
        Customer customer = (Customer) Control.getInstance().getUser();
        customer.sortBuyLogsByLogId();
    }

}
