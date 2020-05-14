package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class SellLog {

    private static HashMap<String, SellLog> allSellLogs = new HashMap<>();
    private String sellLogId;
    private String parentBuyLogId;
    private String sellerId;
    private double receivedMoney;
    private double totalSaleAmount;
    private transient ArrayList<String> logIds;

    public SellLog(String parentBuyLogId, String sellerId) {
        this.parentBuyLogId = parentBuyLogId;
        this.sellerId = sellerId;
        receivedMoney = 0;
        totalSaleAmount = 0;
        allSellLogs.put(sellLogId, this);
        ((Seller)Seller.getAccountByUsername(sellerId)).addSellLog(this);
    }

    public static String generateNewId(String sellerId) {
        //TODO: implement
        return null;
    }

    public static SellLog getSellLogById(String sellLogId) {
        return allSellLogs.get(sellLogId);
    }

    public void initialize() {
        if (sellLogId == null) {
            sellLogId = generateNewId(sellerId);
        }
        allSellLogs.put(sellLogId, this);
        logIds = new ArrayList<>();
    }

    public String getId() {
        return sellLogId;
    }

    private BuyLog getParentBuyLog() {
        return BuyLog.getBuyLogById(parentBuyLogId);
    }


    public double getReceivedMoney() {
        return receivedMoney;
    }

    public double getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public Date getDate() {
        return getParentBuyLog().getDate();
    }

    public String getReceiverName() {
        return getParentBuyLog().getBuyerName();
    }

    public String getReceiverAddress() {
        return getParentBuyLog().getBuyerAddress();
    }

    public String getReceiverPhone() {
        return getParentBuyLog().getBuyerPhoneNumber();
    }

    public LogStatus getLogStatus() {
        return getParentBuyLog().getLogStatus();
    }

    public void setLogStatus(LogStatus status) {
        getParentBuyLog().setLogStatus(status);
    }

//    public ArrayList<Log> getLog() {
//        ArrayList<Log> log = new ArrayList<>();
//        for (String logId : logIds) {
//            log.add(Log.getLogById(logId));
//        }
//        return log;
//    }

//    public void addLogItem(String logItemId) {
//        logItemIds.add(logItemId);
//        Log item = Log.getLogItemById(logItemId);
//        receivedMoney += (item.getPrice() - item.getSaleAmount()) * item.getCount();
//        totalSaleAmount += item.getSaleAmount() * item.getCount();
//    }



}
