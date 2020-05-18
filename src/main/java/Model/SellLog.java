package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Model.*;
public class SellLog implements ModelBase{

    private static HashMap<String, SellLog> allSellLogs = new HashMap<>();
    private static int lastNum=1;
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
        Initialize();
    }

    public static List<SellLog> getAllSellLogs(){
        return new ArrayList<>(allSellLogs.values());
    }

    public static SellLog getSellLogById(String sellLogId) {
        return allSellLogs.get(sellLogId);
    }

    @Override
    public String getId() {
        return sellLogId;
    }

    @Override
    public void Initialize() {
        if (sellLogId == null)
            sellLogId = Methods.generateId(getClass().getSimpleName(),lastNum);
        allSellLogs.put(sellerId,this);
        lastNum++;

        allSellLogs.put(sellLogId, this);
        logIds = new ArrayList<>();
    }

    @Override
    public boolean isSuspend() {
        return false;
    }

    private BuyLog getParentBuyLog() {
        return BuyLog.getBuyLogById(parentBuyLogId);
    }

    public Seller getSeller(){
        return Seller.getSellerById(sellerId,false);
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

    public List<Log> getLog() {
        List<Log> logs = new ArrayList<>();
        for (String logId : logIds){
            logs.add(Log.getLogById(logId));
        }
        return logs;
    }

    public void addLog(String logId){
        logIds.add(logId);
    }



}
