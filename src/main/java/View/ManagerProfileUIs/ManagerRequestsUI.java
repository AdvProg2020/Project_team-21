package View.ManagerProfileUIs;

import Model.Account.Manager;
import Model.Request.Request;
import Model.Request.RequestType;
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
        for (String s : Request.getRequestedOffs().keySet()) {
            
        }
    }

    @Override
    public void help()
    {
        System.out.println("To see details of a request : details [requestId]");
    }
}
