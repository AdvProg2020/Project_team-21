package Client.Model;

import java.util.ArrayList;

public class BuyLog {
    private String logId;
    private String totalDiscountAmount;
    private String price;
    private String date;
    private String receiverPhoneNo;
    private String receiverAddress;
    private String receiverName;

    private ArrayList<Product> allProducts = new ArrayList<>();

    public BuyLog(String logId, String totalDiscountAmount, String price, String date,String receiverAddress,String receiverPhoneNo,String receiverName) {
        this.logId = logId;
        this.totalDiscountAmount = totalDiscountAmount;
        this.price = price;
        this.date = date;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNo = receiverPhoneNo;
        this.receiverName = receiverName;
    }

    public String getLogId() {
        return logId;
    }

    public String getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getReceiverPhoneNo() {
        return receiverPhoneNo;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void addProduct(Product product){
        allProducts.add(product);
    }
}
