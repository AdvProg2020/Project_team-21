package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Model.Methods;
import Model.ModelBase;



public class Log implements ModelBase {
    private static HashMap<String,Log> allLogs= new HashMap<>();
    private static int lastNum = 1;
    private String logId;
    private String buyLogId;
    private String sellLogId;
    private String subProductId;
    private int count;
    private double price;
    private double saleAmount;

    public Log(String buyLogId, String sellLogId, String subProductId, int count) {
        this.buyLogId = buyLogId;
        this.sellLogId = sellLogId;
        this.count = count;
        this.subProductId= subProductId;
//        price=getSubProduct.getRawPrice();
//        saleAmount=price-getSubProduct.getPriceWithOff();



    }

    public static List<Log> getAllLogs(){
        return new ArrayList<>(allLogs.values());
    }

    public static Log getLogById (String logId){
        return allLogs.get(logId);
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

    @Override
    public String getId() {
        return logId;
    }

    @Override
    public void Initialize() {
        if (logId == null)
            logId=Methods.generateId(getClass().getSimpleName(),lastNum);
        allLogs.put(logId,this);
        lastNum++;

        getBuyLog().addLog(logId);
        getSellLog().addLog(logId);
//        getSubProduct().addCustomer(getCustomer().

    }

    @Override
    public boolean isSuspend() {
        return false;
    }

}
