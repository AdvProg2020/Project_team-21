package View.ManagerProfileUIs.ManageRequests;

import Controller.ControlManager;
import Model.Account.Seller;
import Model.Off;
import Model.Product;
import Model.Request.OffRequest;
import Model.Request.ProductRequest;
import Model.Request.Request;
import Model.Request.SellerRequest;
import View.ConsoleView;
import View.UI;

public class ManagerDetailsRequestUI extends UI {
    private static ManagerDetailsRequestUI instance;
    private ManagerDetailsRequestUI()
    {

    }

    public static ManagerDetailsRequestUI getInstance()
    {
        if(instance == null)
            instance = new ManagerDetailsRequestUI();
        return instance;
    }
    private String requestId = "";

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public void run()
    {
        if(ControlManager.getInstance().checkRequestIdExistance(requestId))
        {
            Request request = Request.getAllRequests().get(requestId);
            if(request instanceof SellerRequest)
            {
                Seller seller = Request.getRequestedSellers().get(requestId);
                System.out.println("A seller with these infos is waiting for you! :");
                System.out.println("Username: " + seller.getUsername());
                System.out.println("Name: " + seller.getFirstName() + " " + seller.getLastName());
                System.out.println("Email: " + seller.getEmail());
                System.out.println("Phone number: " + seller.getPhoneNumber());
                System.out.println("Company: " + seller.getCompany().getName());
                System.out.println("Password: " + seller.getPassword());
            }
            else if(request instanceof OffRequest)
            {
                Off off = Request.getRequestedOffs().get(requestId);
                System.out.println("An Off with these infos is waiting for you!");
                System.out.println("Request type: " + request.getRequestType());
                System.out.println("Id: " + off.getOffId());
                System.out.println("Start time: " + off.getStartTime());
                System.out.println("End time: " + off.getEndTime());
                System.out.println("Off percentage: " + off.getOffAmount());
            }
            else if(request instanceof ProductRequest)
            {
                Product product = Request.getRequestedProducts().get(requestId);
                System.out.println("A Product with these infos is waiting for you!");
                System.out.println("Request Type: " + request.getRequestType());
                System.out.println("Name: " + product.getName());
                System.out.println("Id: " + product.getProductId());
                System.out.println("Company name: "+ product.getCompany().getName());
                System.out.println("Price: "+ product.getPrice());
                System.out.println("Category: "+ product.getCategory().getName());
            }
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            ConsoleView.getInstance().errorInput("This request id doesn't exist!" , ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}
