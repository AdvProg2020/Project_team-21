package View.SellerProfileUIs;

import Controller.Control;
import Model.Account.Seller;
import Model.Log.SellLog;
import Model.Product;
import View.ConsoleView;
import View.UI;

public class SellerViewSalesUI extends UI {
    private static SellerViewSalesUI instance;
    private SellerViewSalesUI()
    {

    }

    public static SellerViewSalesUI getInstance()
    {
        if(instance == null)
            instance = new SellerViewSalesUI();
        return instance;
    }
    @Override
    public void run()
    {
        Seller user = (Seller) Control.getInstance().getUser();
        int i = 1;
        int j = 1;
        for (SellLog log : user.getSellLogs())
        {
            System.out.println("Sale No. " + i);
            System.out.println("Sale ID: " + log.getLogId());
            System.out.println("Price: " + log.getPrice());
            System.out.println("Sold to: " + log.getReceiverUserName());
            j = 1;
            for (Product product : log.getAllProductsId())
            {
                System.out.println("Sold Product No. " + j + ": " + product.getProductId());
                j++;
            }
            i++;
        }
        ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
    }

    @Override
    public void help()
    {

    }

}
