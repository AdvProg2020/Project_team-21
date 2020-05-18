package View.SellerProfileUIs.ManageOffs;

import Controller.Control;
import Model.Account.Seller;
import Model.Off;
import View.UI;

public class SellerViewOffsUI extends UI {
    private static SellerViewOffsUI instance;
    private SellerViewOffsUI()
    {

    }

    public static SellerViewOffsUI getInstance()
    {
        if(instance == null)
            instance = new SellerViewOffsUI();
        return instance;
    }
    @Override
    public void run()
    {
        Seller seller = (Seller) Control.getInstance().getUser();
        int i = 1;
        for (Off off : seller.getAllOffs())
        {
            System.out.println(i  +". " + off.getOffId());
        }
    }

    @Override
    public void help()
    {
        System.out.println("To view an Off's infos : view [offID]");
        System.out.println("To send an edit request on an off : edit [offID]");
        System.out.println("To send an add request for an off : add off");
    }

}