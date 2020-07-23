package Server.Model.Log;

import Server.DatabaseHandler;
import Server.Model.Account.Seller;
import Server.Model.Product;
import Server.Model.SaveData;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class SellLog extends Log implements Serializable {
    private static HashMap<String, SellLog> allSellLogs = new HashMap<>();

    public SellLog(String logId, LocalDateTime date, double totalDiscountAmount, double totalAmount, ArrayList<Product> allProducts
            , String sellerUserName, String receiverUserName, String receiverName, String receiverAddress, String receiverPhoneNo, String receiverPostalCode) {
        super(logId, date, totalDiscountAmount, totalAmount, allProducts, sellerUserName, receiverUserName,receiverName,receiverAddress,receiverPhoneNo,receiverPostalCode);
        addSellLog(this);
        for (Seller seller : Seller.getAllSeller()) {
            if(seller.getUsername().equals(sellerUserName))
            {
                seller.addSellLog(this);
            }
        }
        SaveData.saveData(this, getLogId(), SaveData.sellLogFile);
    }
    //    public static void rewriteFiles(){
//        for (String s : SellLog.getAllSellLogs().keySet()) {
//            SellLog log = SellLog.getAllSellLogs().get(s);
//            File file = new File(s+".json");
//            file.delete();
//            SaveData.saveData(log, s, SaveData.sellLogFile);
//        }
//    }
    public static void rewriteFiles(){
        for (String s : SellLog.getAllSellLogs().keySet()) {
            SellLog log = SellLog.getAllSellLogs().get(s);
            SaveData.saveDataRunning(log, s, SaveData.sellLogFile);
        }
    }
    public void addSellLog(SellLog sellLog)
    {
        allSellLogs.put(sellLog.getLogId(),sellLog);
    }
    public void removeBuyLog(SellLog sellLog)
    {
        allSellLogs.remove(sellLog.getLogId(),sellLog);
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.sellLogFile)));
        for (Object object : objects) {
            allSellLogs.put(((SellLog)object).getLogId() ,(SellLog) (object));
        }
    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<SellLog> sellLogs = new ArrayList<>(DatabaseHandler.selectFromSellLog());
        for (SellLog sellLog : sellLogs) {
            allSellLogs.put(sellLog.getLogId(), sellLog);
        }
    }

    public static HashMap<String, SellLog> getAllSellLogs() {
        return allSellLogs;
    }
}
