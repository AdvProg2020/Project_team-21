
package View.OffsUI.OffsMainUI;

import Controller.Control;
import View.UI;


public class ShowProductsWithOffAfterUI extends UI {
    private static ShowProductsWithOffAfterUI instance;

    private ShowProductsWithOffAfterUI()
    {

    }
    public static ShowProductsWithOffAfterUI getInstance() {
        if(instance == null)
            instance = new ShowProductsWithOffAfterUI();
        return instance;
    }

    @Override
    public void run() {
        try {
            System.out.println(Control.getInstance().showFilteredAndSortedProductsWithOff());
        }
        catch (Exception e){

        }

    }

    @Override
    public void help() {
        System.out.println("view categories");
        System.out.println("filtering");
        System.out.println("sorting");
        System.out.println("show products");
        System.out.println("show product [Product ID]");
    }
}

