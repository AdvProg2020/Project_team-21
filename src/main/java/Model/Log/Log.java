package Model.Log;

import Model.DiscountCode;
import Model.Product;
import Model.SaveData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Log implements Comparable<Log>{

    private static HashMap<String, Log> allLogs = new HashMap<>();
    private String logId;
    private LocalDateTime date;
    private double totalDiscountAmount;
    private double price;
    private ArrayList<String> allProducts = new ArrayList<>();
    private String usedDiscountCode;
    private String sellerUserName;
    private String receiverUserName;
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
        for (Product product : allProducts) {
            this.allProducts.add(product.getProductId());
        }
        this.sellerUserName = sellerUserName;
        this.receiverUserName = receiverUserName;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNo = receiverPhoneNo;
        this.receiverPostalCode = receiverPostalCode;
        addLog(this);
//        SaveData.saveData(this, (getLogId()+getReceiverName()), SaveData.lo);
    }

    public void addLog(Log log)
    {
        allLogs.put(log.getLogId(),log);
    }
    public void removeLog(Log log)
    {
        allLogs.remove(log.getLogId(),log);
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
        ArrayList<Product> res = new ArrayList<>();
        for (String product : allProducts) {
            res.add(Product.getAllProducts().get(product));
        }
        return res;
    }
    public void removeProduct(Product product){
        if(allProducts.contains(product.getProductId()))
            allProducts.remove(product.getProductId());
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


    public void setLogId(String logId) {
        this.logId = logId;
    }

    public void setSellerUserName(String sellerUserName) {
        this.sellerUserName = sellerUserName;
    }

    public void setUsedDiscountCode(DiscountCode usedDiscountCode) {

        this.usedDiscountCode = usedDiscountCode.getDiscountId();
    }

    public DiscountCode getUsedDiscountCode() {

        return DiscountCode.getAllDiscountCodes().get(usedDiscountCode);
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

    public static void getObjectFromDatabase(){

        ArrayList<Object> objects1 = new ArrayList<>((SaveData.reloadObject(SaveData.sellLogFile)));
        for (Object object : objects1) {
            allLogs.put(((Log)object).getLogId() ,(Log) (object));
        }

        ArrayList<Object> objects2 = new ArrayList<>((SaveData.reloadObject(SaveData.buyLogFile)));
        for (Object object : objects2) {
            allLogs.put(((Log)object).getLogId() ,(Log) (object));
        }
    }

}
