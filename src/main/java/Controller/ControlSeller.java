package Controller;

import Model.Seller;

import java.util.ArrayList;

public class ControlSeller extends Control{

    public ArrayList<String> viewCompanyName() {
        ArrayList<String> companyName = new ArrayList<>();
        companyName.add(((Seller) currentAccount).getCompanyName());
        return companyName;
    }


}
