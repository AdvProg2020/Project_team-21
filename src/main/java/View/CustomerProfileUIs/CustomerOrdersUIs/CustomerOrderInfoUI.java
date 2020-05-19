package View.CustomerProfileUIs.CustomerOrdersUIs;

import Controller.Control;
import Controller.ControlCustomer;
import Model.Account.Customer;
import Model.Log.BuyLog;
import Model.Product;
import View.ConsoleView;
import View.UI;

public class CustomerOrderInfoUI extends UI {
    private static CustomerOrderInfoUI instance;
    private CustomerOrderInfoUI()
    {

    }

    public static CustomerOrderInfoUI getInstance()
    {
        if(instance == null)
            instance = new CustomerOrderInfoUI();
        return instance;
    }
    String orderID;

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Override
    public void run()
    {
        Customer customer = (Customer) Control.getInstance().getUser();
        if(ControlCustomer.getInstance().checkCustomerGotOrder(orderID,customer))
        {
            BuyLog log = null;
            for (BuyLog buyLog : customer.getBuyLogs())
            {
                if(buyLog.getLogId().equals(orderID))
                {
                    log = buyLog;
                    break;
                }
            }
            System.out.println("List of products on this order: ");
            for (Product product : log.getAllProducts())
            {
                System.out.println("   name: " + product.getName() + "     ID: " + product.getProductId());
            }
            System.out.println();
            System.out.println("Receiver information: ");
            System.out.println("   Name: " + log.getReceiverName());
            System.out.println("   Address: " + log.getReceiverAddress());
            System.out.println("   Postal Code: " + log.getReceiverPostalCode());
            System.out.println("   Phone Number: " + log.getReceiverPhoneNo());
            System.out.println();
            System.out.println("Total discount amount: " + log.getTotalDiscountAmount());
            System.out.println();
            System.out.println("Total payment: " + log.getPrice());
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            ConsoleView.getInstance().errorInput("You don't have an order with this ID.", ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }

}
