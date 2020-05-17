package Model.Request;

import Model.Account.Seller;
import Model.Off;
import Model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffRequest extends Request {
    private Seller provider;
    private String editField;
    private String newValueForField;
    Product product;
    public OffRequest(String requestId, String offId, ArrayList<Product> productList, LocalDateTime startTime, LocalDateTime endTime, double percentage, Seller provider, RequestType requestType)
    {
        super(requestType);
        Off off;
        if(requestType.equals(RequestType.ADD))
            off = new Off(offId,productList,startTime,endTime,percentage);
        off = Off.getAllOffs().get(offId);
        requestedOffs.put(requestId,off);
        Request.addRequest(requestId,this);
        this.provider = provider;
    }

    public void setEditField(String editField) {
        this.editField = editField;
    }

    public void setNewValueForField(String newValueForField) {
        this.newValueForField = newValueForField;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        Off off = Request.getRequestedOffs().get(requestId);
        if(this.getRequestType().equals(RequestType.ADD))
        {
            Off.addOff(Request.getRequestedOffs().get(requestId));
            provider.addOffs(off);
        }
        else if(this.getRequestType().equals(RequestType.DELETE))
        {
            Off.removeOff(Request.getRequestedOffs().get(requestId));
            provider.removeOff(off);
        }
        else if(this.getRequestType().equals(RequestType.EDIT))
        {
            if(editField.equalsIgnoreCase("amount"))
                off.setOffAmount(Double.parseDouble(newValueForField));
            else if(editField.equalsIgnoreCase("start"))
            {
                String[] startDateParsed = newValueForField.split("\\s+");
                LocalDateTime startDateDate = LocalDateTime.of(Integer.parseInt(startDateParsed[0]),Integer.parseInt(startDateParsed[1]),Integer.parseInt(startDateParsed[2]),Integer.parseInt(startDateParsed[3]),Integer.parseInt(startDateParsed[4]));
                off.setStartTime(startDateDate);
            }
            else if(editField.equalsIgnoreCase("end"))
            {
                String[] endDateParsed = newValueForField.split("\\s+");
                LocalDateTime endDateDate = LocalDateTime.of(Integer.parseInt(endDateParsed[0]),Integer.parseInt(endDateParsed[1]),Integer.parseInt(endDateParsed[2]),Integer.parseInt(endDateParsed[3]),Integer.parseInt(endDateParsed[4]));
                off.setEndTime(endDateDate);
            }
            else if(editField.equalsIgnoreCase("add product"))
            {
                off.addProduct(product);
            }
            else if(editField.equalsIgnoreCase("remove product"))
            {
                off.removeProduct(product);
            }
        }
        declineReq(requestId);
    }
}
