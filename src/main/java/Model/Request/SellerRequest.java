package Model.Request;

import GUIControllers.GUICenter;
import Model.Account.Seller;
import Model.Company;
import Model.Product;
import Model.SaveData;

import java.util.ArrayList;

public class SellerRequest extends Request {
    private String providerUsername;
    public SellerRequest(String requestId,String userName, String firstName, String lastName, String email, String phoneNumber, String password, Company company,RequestType requestType,String photo)
    {
        super(requestType);
        Seller seller = new Seller(userName,firstName,lastName,email,phoneNumber,password,company);
        GUICenter.getInstance().setSellerToAddCompany(seller);
        seller.setImagePath(photo);
        Request.addRequest(requestId,this);
        requestedSellers.put(requestId,seller);

        setRequestId(requestId);
        providerUsername = userName;
        SaveData.saveData(this, getRequestId(), SaveData.sellerReqFile);
        SaveData.saveData(seller, (getRequestId()+seller.getUsername()), SaveData.sellerRequestFile);
    }

    public String getProviderUsername() {
        return providerUsername;
    }

    @Override
    public void declineReq(String requestId)
    {
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
            getRequestedSellers().put(((Seller)object).getUsername() ,(Seller) (object));
        }

        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.sellerReqFile)));
        for (Object object : objects2) {
            getAllRequests().put(((SellerRequest)object).getRequestId() ,(SellerRequest) (object));
        }
    }
}
