package View.SellerProfileUIs;

import Controller.Control;
import Model.Account.Account;
import Model.Account.Seller;
import View.ConsoleView;
import View.UI;

public class SellerViewCompanyUI extends UI {
    private static SellerViewCompanyUI instance;
    private SellerViewCompanyUI()
    {

    }

    public static SellerViewCompanyUI getInstance()
    {
        if(instance == null)
            instance = new SellerViewCompanyUI();
        return instance;
    }
    @Override
    public void run()
    {
        Seller user = (Seller)Control.getInstance().getUser();
        System.out.println("Company name: " + user.getCompany().getName());
        System.out.println("Company location: " + user.getCompany().getLocation());
        ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }

}
