package View.ManagerProfileUIs.ManageRequests;

import Controller.ControlManager;
import Model.Request.Request;
import Model.Request.RequestType;
import View.ConsoleView;
import View.UI;

public class ManagerAcceptRequestUI extends UI {
    private static ManagerAcceptRequestUI instance;
    private ManagerAcceptRequestUI()
    {

    }

    public static ManagerAcceptRequestUI getInstance()
    {
        if(instance == null)
            instance = new ManagerAcceptRequestUI();
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
            if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.ADD))
            {
                System.out.println("It has been successfully added!");
            }
            else if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.DELETE))
            {
                System.out.println("It has been successfully deleted!");
            }
            else if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.EDIT))
            {
                System.out.println("It has been successfully edited!");
            }
            Request.getAllRequests().get(requestId).acceptReq(requestId);
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

    @Override
    public void sort() {

    }
}
