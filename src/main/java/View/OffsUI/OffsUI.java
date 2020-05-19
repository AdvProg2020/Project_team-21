package View.OffsUI;

import Controller.Control;
import Model.Product;
import View.UI;


public class OffsUI extends UI {
    private static OffsUI instance;

    private OffsUI()
    {

    }
    public static OffsUI getInstance() {
        if(instance == null)
            instance = new OffsUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println(Control.getInstance().showAllOffProducts());
    }

    @Override
    public void help() {

    }
}
