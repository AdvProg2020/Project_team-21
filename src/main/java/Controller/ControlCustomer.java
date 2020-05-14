package Controller;

import Model.Customer;

public class ControlCustomer extends Control {

    public double viewBalance (){
        return ((Customer) currentAccount).getBalance();
    }
}
