package Model;

import java.util.*;

public class BuyLog implements ModelBase{
    private static HashMap<String , BuyLog> allBuyLogs = new HashMap<>();
    private ArrayList<String> logIds = new ArrayList<>();
    private static int lastNum = 1;
    private double paidMoney;
    private double Discount;
    private String buyLogId;
    private String customerId;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhoneNumber;
    private LogStatus logStatus;
    private Date date;


    public BuyLog(double paidMoney, double discount, String customerId, String buyerName, String buyerAddress,
                  String buyerPhoneNumber, LogStatus logStatus) {
        this.paidMoney = paidMoney;
        this.Discount = discount;
        this.customerId = customerId;
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.buyerPhoneNumber = buyerPhoneNumber;
        this.logStatus = logStatus;
        date=new Date();
        Initialize();


//        if(buyLogId == null)
////            buyLogId = generateNewId (customerId);
        allBuyLogs.put(buyLogId,this);
        ((Customer)Customer.getAccountByUsername(customerId)).addBuyLogs(this);
    }

    public static BuyLog getBuyLogById(String buyLogId){
        return allBuyLogs.get(buyLogId);
    }

    public void setLogStatus(LogStatus logStatus) {
        this.logStatus = logStatus;
    }

    public double getPaidMoney() {
        return paidMoney;
    }

    public double getDiscount() {
        return Discount;
    }

    public String getBuyLogId() {
        return buyLogId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public LogStatus getLogStatus() {
        return logStatus;
    }

    public Date getDate() {
        return date;
    }

    public static List<BuyLog> getAllBuyLogs(){
        return new ArrayList<>(allBuyLogs.values());
    }

    public static BuyLog getBuyLogId (String buyLogId){
        return allBuyLogs.get(buyLogId);
    }

    public List<Log> getLogs(){
        List<Log> logs = new ArrayList<>();
        for (String logId : logIds){
            logs.add(Log.getLogById(logId));
        }
        return logs;
    }

    public void addLog(String logId){
        logIds.add(logId);
    }

    @Override
    public String getId() {
        return buyLogId;
    }

    @Override
    public void Initialize() {
        if (buyLogId==null)
            buyLogId=Methods.generateId(getClass().getSimpleName(),lastNum);
        allBuyLogs.put(buyLogId,this);
        lastNum++;
        logIds =new ArrayList<>();
        Customer.getCustomerById(customerId,false).addBuyLogs(this);
    }

    @Override
    public boolean isSuspend() {
        return false;
    }
}
