package Server.Model.Log;

import Server.DatabaseHandler;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Product;
import Server.Model.SaveData;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyLog extends Log implements Serializable {

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
            if(customer.getUsername().equals(receiverUserName))
            {
                customer.addBuyLogs(this);
            }
        }
//        SaveData.saveData(this, getLogId(), SaveData.buyLogFile);
    }
    //    public static void rewriteFiles(){
//        for (String s : BuyLog.getAllBuyLogs().keySet()) {
//            BuyLog log = BuyLog.getAllBuyLogs().get(s);
//            File file = new File(s+".json");
//            file.delete();
//            SaveData.saveData(log, s, SaveData.buyLogFile);
//        }
//    }
//    public static void rewriteFiles(){
//        for (String s : BuyLog.getAllBuyLogs().keySet()) {
//            BuyLog log = BuyLog.getAllBuyLogs().get(s);
//            SaveData.saveDataRunning(log, s, SaveData.buyLogFile);
//        }
//    }

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

//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.buyLogFile)));
//        for (Object object : objects) {
//            allBuyLogs.put(((BuyLog)object).getLogId() ,(BuyLog) (object));
//        }
//    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<BuyLog> buyLogs = new ArrayList<>(DatabaseHandler.selectFromBuyLog());
        for (BuyLog buyLog : buyLogs) {
            allBuyLogs.put(buyLog.getLogId(), buyLog);
        }
    }

    public static HashMap<String, BuyLog> getAllBuyLogs() {
        return allBuyLogs;
    }
}
