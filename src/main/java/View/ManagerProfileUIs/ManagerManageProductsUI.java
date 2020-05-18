package View.ManagerProfileUIs;

import Model.Product;
import View.UI;

public class ManagerManageProductsUI extends UI {
    private static ManagerManageProductsUI instance;
    private ManagerManageProductsUI()
    {

    }

    public static ManagerManageProductsUI getInstance()
    {
        if(instance == null)
            instance = new ManagerManageProductsUI();
        return instance;
    }
    @Override
    public void run() {
        for (String s : Product.getAllProducts().keySet()) {
            System.out.println("name: "+Product.getAllProducts().get(s).getName() + "id: " + s);
        }
    }

    @Override
    public void help() {
        System.out.println("to remove a product : remove [productId]");
    }

    @Override
    public void sort() {

    }
}
