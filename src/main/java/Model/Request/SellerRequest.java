package Model.Request;

import Model.Account.Seller;
import Model.Company;
import Model.Product;

public class SellerRequest extends Request {
    public SellerRequest(String requestId,String userName, String firstName, String lastName, String email, String phoneNumber, String password, Company company,RequestType requestType)
    {
        super(requestType);
        Seller seller = new Seller(userName,firstName,lastName,email,phoneNumber,password,company);
        Request.addRequest(requestId,this);
        requestedSellers.put(requestId,seller);
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
}
