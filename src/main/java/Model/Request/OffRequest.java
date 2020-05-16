package Model.Request;

import Model.Account.Seller;
import Model.Off;
import Model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffRequest extends Request {
    Seller provider;
    public OffRequest(String requestId, String offId, ArrayList<Product> productList, LocalDateTime startTime, LocalDateTime endTime, double percentage, Seller provider, RequestType requestType)
    {
        Off off = new Off(offId,productList,startTime,endTime,percentage);
        requestedOffs.put(requestId,off);
        Request.addRequest(requestId,this);
        this.provider = provider;
        this.setRequestType(requestType);
    }

    public Seller getProvider() {
        return provider;
    }

    @Override
    public void declineReq(String requestId)
    {
        requestedOffs.remove(requestId);
        Request.getAllRequests().remove(requestId);
    }

    @Override
    public void acceptReq(String requestId)
    {
        Off.addOff(Request.getRequestedOffs().get(requestId));
        declineReq(requestId);
    }
}
