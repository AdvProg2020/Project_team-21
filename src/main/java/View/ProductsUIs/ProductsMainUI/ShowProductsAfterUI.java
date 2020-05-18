

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
        try {
            System.out.println(Control.getInstance().showFilteredAndSortedProducts()) ;
        }catch (Exception e){
        }
    }

    @Override
    public void help() {
    }
}