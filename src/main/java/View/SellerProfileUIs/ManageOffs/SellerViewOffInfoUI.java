package View.SellerProfileUIs.ManageOffs;

import Controller.Control;
import Controller.ControlManager;
import Controller.ControlSeller;
import Model.Account.Seller;
import Model.Off;
import Model.Product;
import View.ConsoleView;
import View.UI;

public class SellerViewOffInfoUI extends UI {
    private static SellerViewOffInfoUI instance;
    private SellerViewOffInfoUI()
    {

    }

    public static SellerViewOffInfoUI getInstance()
    {
        if(instance == null)
            instance = new SellerViewOffInfoUI();
        return instance;
    }
    String offID;

    public void setOffID(String offID) {
        this.offID = offID;
    }

    @Override
    public void run()
    {
        Seller seller = (Seller) Control.getInstance().getUser();
        if(ControlSeller.getInstance().checkOffExistance(offID) && ControlSeller.getInstance().checkSellerGotOff(offID,seller))
        {
            Off off = Off.getAllOffs().get(offID);
            System.out.println("Start time: " + off.getStartTime());
            System.out.println("End time: " + off.getEndTime());
            System.out.println("Products: ");
            int i=1;
            for (Product product : off.getProductsList())
            {
                System.out.println(i + ". " + product.getName() + "    " + product.getProductId());
                i++;
            }
            System.out.println("Off amount: " + off.getOffAmount());
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            ConsoleView.getInstance().errorInput("This off does not exist!" , ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }

}
