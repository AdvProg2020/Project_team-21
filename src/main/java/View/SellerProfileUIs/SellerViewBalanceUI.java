package View.SellerProfileUIs;

import Controller.Control;
import Model.Account.Seller;
import View.ConsoleView;
import View.UI;

public class SellerViewBalanceUI extends UI {
    private static SellerViewBalanceUI instance;
    private SellerViewBalanceUI()
    {

    }

    public static SellerViewBalanceUI getInstance()
    {
        if(instance == null)
            instance = new SellerViewBalanceUI();
        return instance;
    }
    @Override
    public void run()
    {
        Seller seller = (Seller) Control.getInstance().getUser();
        System.out.println("Your balance is " + seller.getCredit());
        ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
    }

    @Override
    public void help() {

    }

}
