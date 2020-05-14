package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyLog {
    private static HashMap<String , BuyLog> allBuyLogs = new HashMap<>();
    private ArrayList<String> logItemsId = new ArrayList<>();
    private double paidMoney;
    private double Discount;
    private String buyLogId;
    private String customerUsername;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhoneNumber;
    private LogStatus logStatus;
    private Date date;


    public BuyLog(double paidMoney, double discount, String customerUsername, String buyerName, String buyerAddress,
                  String buyerPhoneNumber, LogStatus logStatus) {
        this.paidMoney = paidMoney;
        Discount = discount;
        this.customerUsername = customerUsername;
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.buyerPhoneNumber = buyerPhoneNumber;
        this.logStatus = logStatus;

//        if(buyLogId == null)
////            buyLogId = generateNewId (customerId);
        allBuyLogs.put(buyLogId,this);
        ((Customer)Customer.getAccountByUsername(customerUsername)).addBuyLogs(this);
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

    public String getCustomerUsername() {
        return customerUsername;
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


}
