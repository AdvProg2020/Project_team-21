package Model.Request;

import Model.Account.Seller;
import Model.Off;
import Model.Product;

import java.util.HashMap;

public abstract class Request {
    private static HashMap<String, Request> allRequests = new HashMap<>();
    protected static HashMap<String, Product> requestedProducts = new HashMap<>();
    protected static HashMap<String, Off> requestedOffs = new HashMap<>();
    protected static HashMap<String, Seller> requestedSellers = new HashMap<>();

    private String requestId;
    public static HashMap<String, Off> getRequestedOffs() {
        return requestedOffs;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public static void addRequest(String requestId,Request request)
    {
        allRequests.put(requestId,request);
    }

    public static HashMap<String, Request> getAllRequests() {
        return allRequests;
    }

    public static HashMap<String, Product> getRequestedProducts() {
        return requestedProducts;
    }
    public abstract void declineReq(String requestId);

    public abstract void acceptReq(String requestId);

    public static HashMap<String, Seller> getRequestedSellers() {
        return requestedSellers;
    }
}
