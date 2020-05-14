package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Log {
    private static HashMap<String,Log> allLogs= new HashMap<>();
    private String logId;
    private String buyLogId;
    private String sellLogId;
    private int count;
    private double price;
    private double saleAmount;

    public Log(String buyLogId, String sellLogId, int count) {
        this.buyLogId = buyLogId;
        this.sellLogId = sellLogId;
        this.count = count;
//        price
//        saleAmount
        allLogs.put(logId,this);
//        getBuyLog().addLogItem
//        getSellLog().addLogItem
    }

    public String getLogId() {
        return logId;
    }

    public BuyLog getBuyLog(){
        return BuyLog.getBuyLogById(buyLogId);
    }

    public SellLog getSellLog(){
        return SellLog.getSellLogById(sellLogId);
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public double getSaleAmount() {
        return saleAmount;
    }
}
