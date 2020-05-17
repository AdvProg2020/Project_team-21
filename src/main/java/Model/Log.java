package Model;

import java.util.ArrayList;
import java.util.Date;

public class Log implements Comparable<Log>{
    private String logId;
    private Date date;
    private double totalDiscountAmount;
    private double price;
    private ArrayList<Product> allProducts= new ArrayList<>();
    private String sellerUserName;
    private String receiverUserName;
    private LogStatus logStatus;

    public Log(String logId, Date date, double totalDiscountAmount, double totalAmount, ArrayList<Product> allProducts,
               String sellerUserName, String receiverUserName) {
        this.logId = logId;
        this.date = date;
        this.totalDiscountAmount = totalDiscountAmount;
        this.price = totalAmount;
        this.allProducts = allProducts;
        this.sellerUserName = sellerUserName;
        this.receiverUserName = receiverUserName;
    }

    public void setLogStatus(LogStatus logStatus) {
        this.logStatus = logStatus;
    }

    public String getLogId() {
        return logId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


    public ArrayList<Product> getAllProductsId() {
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

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    @Override
    public int compareTo(Log o) {
        return getLogId().compareTo(o.getLogId());
    }
//    public boolean hasProductWithId (String Id){
//
//    }

//    public Product getProductWithId (String Id){
//
//    }
}
