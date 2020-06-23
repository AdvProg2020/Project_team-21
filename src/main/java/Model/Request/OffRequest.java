package Model.Request;

import Model.Account.Seller;
import Model.Off;
import Model.Product;
import Model.SaveData;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffRequest extends Request {
    private Seller provider;
    private String editField;
    private String newValueForField;
    private String providerUsername;
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
        providerUsername = provider.getUsername();
        setRequestId(requestId);
        SaveData.saveData(this, getRequestId(), SaveData.offReqFile);
        SaveData.saveData(off, (getRequestId()+off.getOffId()), SaveData.offRequestFile);
    }

    public String getProviderUsername() {
        return providerUsername;
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
        File file = new File(requestId +(requestedOffs.get(requestId).getOffId())+".txt");
        file.delete();
        File file1 = new File(requestId+".txt");
        file1.delete();
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
            for (Product product1 : off.getProductsList()) {
                product1.setOff(off);
            }
        }
        else if(this.getRequestType().equals(RequestType.DELETE))
        {
            Off.removeOff(Request.getRequestedOffs().get(requestId));
            provider.removeOff(off);
            for (Product product1 : off.getProductsList()) {
                product1.setOff(null);
            }
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
                product.setOff(off);
            }
            else if(editField.equalsIgnoreCase("remove product"))
            {
                product.setOff(null);
                off.removeProduct(product);
            }
        }
        declineReq(requestId);
    }

    @Override
    public String getType() {
        return "Off Request";
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.offRequestFile)));
        for (Object object : objects) {
            getRequestedOffs().put(((Off)object).getOffId() ,(Off) (object));
        }

        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.offReqFile)));
        for (Object object : objects2) {
            getAllRequests().put(((OffRequest)object).getRequestId() ,(OffRequest) (object));
        }
    }
}
