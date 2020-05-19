package View.SellerProfileUIs;

import Controller.ControlSeller;
import Model.Product;
import Model.Request.ProductRequest;
import View.ConsoleView;
import View.UI;

public class SellerRemoveProductReqUI extends UI {
    private static SellerRemoveProductReqUI instance;
    private SellerRemoveProductReqUI()
    {

    }

    public static SellerRemoveProductReqUI getInstance()
    {
        if(instance == null)
            instance = new SellerRemoveProductReqUI();
        return instance;
    }
    String productID;

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public void run()
    {
        try
        {
           String reqID = ControlSeller.getInstance().sendRemoveProductReq(productID);
           System.out.println("Your request with ID " + reqID + " has been sent!");
           ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help()
    {

    }

    @Override
    public void sort() {

    }

}
