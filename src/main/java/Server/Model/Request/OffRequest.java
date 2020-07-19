package Server.Model.Request;

import Server.Model.Account.Seller;
import Server.Model.Off;
import Server.Model.Product;
import Server.Model.SaveData;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OffRequest extends Request {
    private String provider;
    private String editField;
    private String newValueForField;

    String product;

    public OffRequest(String requestId, String offId, ArrayList<Product> productList, LocalDateTime startTime, LocalDateTime endTime, double percentage, Seller provider, RequestType requestType)
    {
        super(requestType);
        Off off;
        if(requestType.equals(RequestType.ADD))
            off = new Off(offId,productList,startTime,endTime,percentage);
        else
            off = Off.getAllOffs().get(offId);
        off.setRequestID(requestId);
        requestedOffs.put(requestId,off);
        Request.addRequest(requestId,this);
        this.provider = provider.getUsername();
        setProviderUsername(provider.getUsername());
        setRequestId(requestId);
        SaveData.saveData(this, getRequestId(), SaveData.offReqFile);
        SaveData.saveData(off, (getRequestId()+off.getOffId()), SaveData.offRequestFile);
    }

    //    public static void rewriteFiles(){
//        for (String s : getAllRequests().keySet()) {
//            Request req = getAllRequests().get(s);
//            File file = new File(s+".json");
//            file.delete();
//            if(req.getType().equalsIgnoreCase("Off Request"))
//                SaveData.saveData(req, s, SaveData.offReqFile);
//            else if(req.getType().equalsIgnoreCase("Product Request"))
//                SaveData.saveData(req, s, SaveData.productReqFile);
//            else if(req.getType().equalsIgnoreCase("Seller Request"))
//                SaveData.saveData(req, s, SaveData.sellerReqFile);
//        }
//        for (String s : requestedOffs.keySet()) {
//            Off off = requestedOffs.get(s);
//            File file = new File(s + off.getOffId()+".txt");
//            file.delete();
//            SaveData.saveData(off, s+off.getOffId(), SaveData.offRequestFile);
//        }
//    }
    public static void rewriteFiles(){
        for (String s : getAllRequests().keySet()) {
            Request req = getAllRequests().get(s);
            if(req.getType().equalsIgnoreCase("Off Request"))
                SaveData.saveDataRunning(req, s, SaveData.offReqFile);
            else if(req.getType().equalsIgnoreCase("Product Request"))
                SaveData.saveDataRunning(req, s, SaveData.productReqFile);
            else if(req.getType().equalsIgnoreCase("Seller Request"))
                SaveData.saveDataRunning(req, s, SaveData.sellerReqFile);
        }
        for (String s : requestedOffs.keySet()) {
            Off off = requestedOffs.get(s);
            SaveData.saveDataRunning(off, s+off.getOffId(), SaveData.offRequestFile);
        }
    }

    public void setEditField(String editField) {
        this.editField = editField;
    }

    public void setNewValueForField(String newValueForField) {
        this.newValueForField = newValueForField;
    }

    public void setProduct(Product product) {

        this.product = product.getProductId();
    }

    public Seller getProvider() {
        Seller res = null;
        for (Seller seller : Seller.getAllSeller()) {
            if(seller.getUsername().equalsIgnoreCase(provider))
                res = seller;

        }
        return res;
    }

    @Override
    public void declineReq(String requestId)
    {
        File file = new File(requestId +(requestedOffs.get(requestId).getOffId())+".json");
        file.delete();
        File file1 = new File(requestId+".json");
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
            getProvider().addOffs(off);
            for (Product product1 : off.getProductsList()) {
                product1.setOff(off);
            }
        }
        else if(this.getRequestType().equals(RequestType.DELETE))
        {
            Off.removeOff(Request.getRequestedOffs().get(requestId));
            getProvider().removeOff(off);
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
                off.addProduct(getProduct());
                getProduct().setOff(off);
            }
            else if(editField.equalsIgnoreCase("remove product"))
            {
                getProduct().setOff(null);
                off.removeProduct(getProduct());
            }
        }
        declineReq(requestId);
    }

    public Product getProduct() {
        return Product.getAllProducts().get(product);
    }

    @Override
    public String getType() {
        return "Off Request";
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.offRequestFile)));
        for (Object object : objects) {
            getRequestedOffs().put(((Off)object).getRequestID() ,(Off) (object));
        }

        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.offReqFile)));
        for (Object object : objects2) {
            getAllRequests().put(((OffRequest)object).getRequestId() ,(OffRequest) (object));
        }
    }
}
