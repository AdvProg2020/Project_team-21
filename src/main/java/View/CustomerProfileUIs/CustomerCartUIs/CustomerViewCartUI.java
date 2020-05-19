package View.CustomerProfileUIs.CustomerCartUIs;

import View.UI;

public class CustomerViewCartUI extends UI {
    private static CustomerViewCartUI instance;
    private CustomerViewCartUI()
    {

    }

    public static CustomerViewCartUI getInstance()
    {
        if(instance == null)
            instance = new CustomerViewCartUI();
        return instance;
    }
    @Override
    public void run()
    {
        System.out.println("welcome to your cart!\nType a command!");
    }

    @Override
    public void help()
    {
        System.out.println("To show products in your cart : show products");
        System.out.println("To redirect to a product's page : view [productID]");
        System.out.println("To increase the quantity of an item : increase [productID]");
        System.out.println("To decrease the quantity of an item : decrease [productID]");
        System.out.println("To show the total price of cart : show total price");
        System.out.println("To finalize your purchase : purchase");
    }

    @Override
    public void sort() {

    }

}
