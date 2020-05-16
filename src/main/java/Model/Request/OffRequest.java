package Model.Request;

import Model.Off;
import Model.Product;

import java.rmi.registry.LocateRegistry;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffRequest extends Request {
    public OffRequest(String requestId,String offId , ArrayList<Product> productList, LocalDateTime startTime, LocalDateTime endTime,double percentage)
    {
        Off off = new Off(offId,productList,startTime,endTime,percentage);
        requestedOffs.put(requestId,off);
        Request.addRequest(requestId,this);
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
