package Model.Request;

import GUIControllers.GUICenter;
import Model.Account.Seller;
import Model.Company;
import Model.Product;
import Model.SaveData;

import java.io.File;
import java.util.ArrayList;

public class SellerRequest extends Request {
    private String providerUsername;
    public SellerRequest(String requestId,String userName, String firstName, String lastName, String email, String phoneNumber, String password, Company company,RequestType requestType,String photo)
    {
        super(requestType);
        Seller seller = new Seller(userName,firstName,lastName,email,phoneNumber,password,company,photo);
        seller.setRequestID(requestId);
        GUICenter.getInstance().setSellerToAddCompany(seller);
        Request.addRequest(requestId,this);
        requestedSellers.put(requestId,seller);

        setRequestId(requestId);
        providerUsername = userName;
        SaveData.saveData(seller, (getRequestId()+seller.getUsername()), SaveData.sellerRequestFile);
        SaveData.saveData(this, getRequestId(), SaveData.sellerReqFile);
    }
    //    public static void rewriteFiles(){
//        for (String s : requestedSellers.keySet()) {
//            Seller seller = requestedSellers.get(s);
//            File file = new File(s + seller.getUsername()+".json");
//            file.delete();
//            SaveData.saveData(seller, s+seller.getUsername(), SaveData.sellerRequestFile);
//        }
//    }
    public static void rewriteFiles(){
        for (String s : requestedSellers.keySet()) {
            Seller seller = requestedSellers.get(s);
            SaveData.saveDataRunning(seller, s+seller.getUsername(), SaveData.sellerRequestFile);
        }
    }

    public String getProviderUsername() {
        return providerUsername;
    }

    @Override
    public void declineReq(String requestId)
    {
        File file = new File(requestId+(requestedSellers.get(requestId).getUsername())+".json");
        file.delete();
        File file1 = new File(requestId+".json");
        file1.delete();
        requestedSellers.remove(requestId);
        getAllRequests().remove(requestId);
    }

    @Override
    public void acceptReq(String requestId)
    {
        Seller.addNewSeller(requestedSellers.get(requestId));
        declineReq(requestId);
    }

    @Override
    public String getType() {
        return "Seller Request";
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.sellerRequestFile)));
        for (Object object : objects) {
            requestedSellers.put(((Seller)object).getRequestID() ,(Seller) (object));
        }

        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.sellerReqFile)));
        for (Object object : objects2) {
            getAllRequests().put(((SellerRequest)object).getRequestId() ,(SellerRequest) (object));
        }
    }
}
