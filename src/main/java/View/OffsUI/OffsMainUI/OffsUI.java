package View.OffsUI.OffsMainUI;

import Controller.Control;
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
        System.out.println("view categories");
        System.out.println("filtering");
        System.out.println("sorting");
        System.out.println("show products");
        System.out.println("show product [Product ID]");
    }
}
