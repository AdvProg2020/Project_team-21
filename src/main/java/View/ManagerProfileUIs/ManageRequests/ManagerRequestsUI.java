package View.ManagerProfileUIs.ManageRequests;

import Model.Request.OffRequest;
import Model.Request.ProductRequest;
import Model.Request.Request;
import View.UI;

public class ManagerRequestsUI extends UI {
    private static ManagerRequestsUI instance;
    private ManagerRequestsUI()
    {

    }

    public static ManagerRequestsUI getInstance()
    {
        if(instance == null)
            instance = new ManagerRequestsUI();
        return instance;
    }
    @Override
    public void run()
    {
        System.out.println("Off requests: ");
        for (String s : Request.getRequestedOffs().keySet())
        {
            System.out.println("From: " + ((OffRequest)Request.getAllRequests().get(s)).getProvider().getUsername() + "  request id: " + s);
        }

        System.out.println("Product requests :");
        for (String s : Request.getRequestedProducts().keySet())
        {
            System.out.println("From: " + ((ProductRequest)Request.getAllRequests().get(s)).getProvider().getUsername() + "  request id: " + s);
        }

        System.out.println("Seller activation requests: ");
        for (String s : Request.getRequestedSellers().keySet())
        {
            System.out.println("Username: " + Request.getRequestedSellers().get(s).getUsername() + "  request id: " + s);
        }
    }

    @Override
    public void help()
    {
        System.out.println("To see details of a request : details [requestId]");
        System.out.println("To accept or decline a request : accpet/decline [requestId]");
    }
}
