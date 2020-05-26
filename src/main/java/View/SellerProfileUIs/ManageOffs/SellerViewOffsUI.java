package View.SellerProfileUIs.ManageOffs;

import Controller.Control;
import Model.Account.Seller;
import Model.Off;
import View.SellerProfileUIs.SortOffType;
import View.UI;

public class SellerViewOffsUI extends UI {
    private static SellerViewOffsUI instance;
    private SortOffType sortOffType;
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
        System.out.println("To sort offs according to off id: sort offs by offId");
        System.out.println("To sort offs according to off amount: sort offs by off amount");
    }

    public void setSortOffType(SortOffType sortOffType) {
        this.sortOffType = sortOffType;
    }

    private SortOffType getSortOffType() {
        return sortOffType;
    }

    @Override
    public void sort() {
        if(getSortOffType()==SortOffType.OFF_ID){
            Off.sortAllOffsByOffID();
        } else if(getSortOffType()==SortOffType.OFF_AMOUNT){
            Off.sortAllOffsByOffAmount();
        }
    }
}
