package Server.Model.Request;

import Server.DatabaseHandler;
import Server.Model.Account.Seller;
import Server.Model.Company;
import Server.Model.SaveData;
import Server.ServerCenter;

import java.io.File;
import java.util.ArrayList;

public class SellerRequest extends Request {

    public SellerRequest(String requestId,String userName, String firstName, String lastName, String email, String phoneNumber, String password, Company company,RequestType requestType,String photo)
    {
        super(requestType);

        setRequestId(requestId);
        setProviderUsername(userName);
//        ServerCenter.getInstance().setSellerToAddCompany(seller);

        Request.addRequest(requestId,this);
        Seller seller = new Seller(userName,firstName,lastName,email,phoneNumber,password,company,photo);
        seller.setRequestID(requestId);
        requestedSellers.put(requestId,seller);
        System.out.println("0");
//        SaveData.saveData(this, requestId, SaveData.sellerReqFile);
        System.out.println("1");
//        SaveData.saveData(seller, requestId+userName, SaveData.sellerRequestFile);
        System.out.println("2");
    }

//    public static void rewriteFiles(){
//        for (String s : requestedSellers.keySet()) {
//            Seller seller = requestedSellers.get(s);
//            SaveData.saveDataRunning(seller, s+seller.getUsername(), SaveData.sellerRequestFile);
//        }
//    }

    @Override
    public void declineReq(String requestId)
    {
//        File file = new File("Database/" + requestId+(requestedSellers.get(requestId).getUsername())+".json");
//        file.delete();
//        File file1 = new File("Database/" + requestId+".json");
//        file1.delete();
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

//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.sellerRequestFile)));
//        for (Object object : objects) {
//            requestedSellers.put(((Seller)object).getRequestID() ,(Seller) (object));
//        }
//
//        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.sellerReqFile)));
//        for (Object object : objects2) {
//            getAllRequests().put(((SellerRequest)object).getRequestId() ,(SellerRequest) (object));
//        }
//    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Seller> sellers = new ArrayList<>(DatabaseHandler.selectFromSellerAtRequest());
        for (Seller seller : sellers) {
            requestedSellers.put(seller.getRequestID(), seller);
        }

        ArrayList<SellerRequest> sellerRequests = new ArrayList<>(DatabaseHandler.selectFromSellerRequest());
        for (SellerRequest sellerRequest : sellerRequests) {
            getAllRequests().put(sellerRequest.getRequestId(), sellerRequest);
        }
    }
}
