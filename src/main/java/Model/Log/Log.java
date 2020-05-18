package Model.Log;

import Model.DiscountCode;
import Model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Log {
    private static HashMap<String, Log> allLogs = new HashMap<>();
    private String logId;
    private LocalDateTime date;
    private double totalDiscountAmount;
    private double price;
    private ArrayList<Product> allProducts= new ArrayList<>();
    private String sellerUserName;
    private String receiverUserName;
    private LogStatus logStatus;
    private DiscountCode usedDiscountCode;

    private String receiverName;
    private String receiverAddress;
    private String receiverPhoneNo;
    private String receiverPostalCode;

    public Log(String logId, LocalDateTime date, double totalDiscountAmount, double totalAmount, ArrayList<Product> allProducts,
               String sellerUserName, String receiverUserName, String receiverName, String receiverAddress, String receiverPhoneNo, String receiverPostalCode) {
        this.logId = logId;
        this.date = date;
        this.totalDiscountAmount = totalDiscountAmount;
        this.price = totalAmount;
        this.allProducts = allProducts;
        this.sellerUserName = sellerUserName;
        this.receiverUserName = receiverUserName;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNo = receiverPhoneNo;
        this.receiverPostalCode = receiverPostalCode;
        logStatus = LogStatus.PROCESSING;
        addLog(this);
    }

    public void addLog(Log log)
    {
        allLogs.put(log.getLogId(),log);
    }
    public void removeLog(Log log)
    {
        allLogs.remove(log.getLogId(),log);
    }
    public void setLogStatus(LogStatus logStatus) {
        this.logStatus = logStatus;
    }

    public String getLogId() {
        return logId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public String getSellerName() {
        return sellerUserName;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public String getSellerUserName() {
        return sellerUserName;
    }

    public LogStatus getLogStatus() {
        return logStatus;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public void setSellerUserName(String sellerUserName) {
        this.sellerUserName = sellerUserName;
    }

    public void setUsedDiscountCode(DiscountCode usedDiscountCode) {
        this.usedDiscountCode = usedDiscountCode;
    }

    public DiscountCode getUsedDiscountCode() {
        return usedDiscountCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getReceiverPhoneNo() {
        return receiverPhoneNo;
    }

    public String getReceiverPostalCode() {
        return receiverPostalCode;
    }

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }
//    public boolean hasProductWithId (String Id){
//
//    }

//    public Product getProductWithId (String Id){
//
//    }
}