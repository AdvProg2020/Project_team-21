package Model.Log;

import Model.Account.Customer;
import Model.Product;
import Model.SaveData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyLog extends Log{
    private static HashMap<String, BuyLog> allBuyLogs = new HashMap<>();
    private ArrayList<String> sellersUsernames = new ArrayList<>();
    public BuyLog(String logId, LocalDateTime date, double totalDiscountAmount, double totalAmount, ArrayList<Product> allProducts, String sellerUserName, String receiverUserName
            , String receiverName, String receiverAddress, String receiverPhoneNo, String receiverPostalCode,ArrayList<String> sellersUsernames)
    {
        super(logId, date, totalDiscountAmount, totalAmount, allProducts, sellerUserName, receiverUserName,receiverName,receiverAddress,receiverPhoneNo,receiverPostalCode);
        addSellLog(this);
        for (String username : sellersUsernames) {
            this.sellersUsernames.add(username);
        }
        for (Customer customer : Customer.getaAllCustomers()) {
            if(customer.getUsername().equalsIgnoreCase(receiverUserName))
            {
                customer.addBuyLogs(this);
            }
        }
        SaveData.saveData(this, getLogId(), SaveData.buyLogFile);
    }

    public ArrayList<String> getSellersUsernames() {
        return sellersUsernames;
    }

    public void addSellLog(BuyLog buyLog)
    {
        allBuyLogs.put(buyLog.getLogId(),buyLog);
    }
    public void removeSellLog(BuyLog buyLog)
    {
        allBuyLogs.remove(buyLog.getLogId(),buyLog);
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.buyLogFile)));
        for (Object object : objects) {
            allBuyLogs.put(((BuyLog)object).getLogId() ,(BuyLog) (object));
        }
    }
}
