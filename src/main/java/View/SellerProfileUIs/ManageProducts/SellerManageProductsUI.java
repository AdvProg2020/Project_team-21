package View.SellerProfileUIs.ManageProducts;

import Controller.Control;
import Model.Account.Seller;
import Model.Product;
import View.UI;

public class SellerManageProductsUI extends UI {
    private static SellerManageProductsUI instance;
    private SellerManageProductsUI()
    {

    }

    public static SellerManageProductsUI getInstance()
    {
        if(instance == null)
            instance = new SellerManageProductsUI();
        return instance;
    }
    @Override
    public void run()
    {
        Seller user = (Seller) Control.getInstance().getUser();
        int i = 1;
        for (Product product : user.getAllProducts()) {
            System.out.println("Product No. " + i + "ID: " + product.getProductId());
            System.out.println("Product's name: " + product.getName());
            i++;
        }
    }

    @Override
    public void help()
    {
        System.out.println("To see a product's info : view [productID]");
        System.out.println("To view buyers of a product : view buyers [productID]");
        System.out.println("To request a change in a product : edit [productID]");
    }

    @Override
    public void sort() {

    }

}
