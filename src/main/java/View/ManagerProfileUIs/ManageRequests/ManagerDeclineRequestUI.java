package View.ManagerProfileUIs.ManageRequests;

import Controller.ControlManager;
import Model.Request.Request;
import View.ConsoleView;
import View.UI;

public class ManagerDeclineRequestUI extends UI {
    private static ManagerDeclineRequestUI instance;
    private ManagerDeclineRequestUI()
    {

    }

    public static ManagerDeclineRequestUI getInstance()
    {
        if(instance == null)
            instance = new ManagerDeclineRequestUI();
        return instance;
    }
    private String requestId;

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public void run()
    {
        if(ControlManager.getInstance().checkRequestIdExistance(requestId))
        {
            Request.getAllRequests().get(requestId).declineReq(requestId);
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            ConsoleView.getInstance().errorInput("This request ID doesn't exist!" , ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }
}
