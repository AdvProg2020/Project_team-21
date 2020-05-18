

package View.ProductsUIs.ProductsMainUI;

import Controller.Control;
import View.UI;
import Model.Product;

public class ShowProductsAfterUI extends UI {
    private static ShowProductsAfterUI instance;

    private ShowProductsAfterUI()
    {

    }
    public static ShowProductsAfterUI getInstance() {
        if(instance == null)
            instance = new ShowProductsAfterUI();
        return instance;
    }

    @Override
    public void run()  {
//        System.out.println(Control.getInstance().showFilteredAndSortedProducts()) ;
    }

    @Override
    public void help() {
    }
}