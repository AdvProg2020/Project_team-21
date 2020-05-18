package Model.Log;

import Model.Account.Seller;
import Model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class SellLog extends Log{
    private static HashMap<String, SellLog> allSellLogs = new HashMap<>();
    public SellLog(String logId, LocalDateTime date, double totalDiscountAmount, double totalAmount, ArrayList<Product> allProducts
            , String sellerUserName, String receiverUserName, String receiverName, String receiverAddress, String receiverPhoneNo, String receiverPostalCode) {
        super(logId, date, totalDiscountAmount, totalAmount, allProducts, sellerUserName, receiverUserName,receiverName,receiverAddress,receiverPhoneNo,receiverPostalCode);
        addSellLog(this);
        for (Seller seller : Seller.getAllSeller()) {
            if(seller.getUsername().equalsIgnoreCase(sellerUserName))
            {
                seller.addSellLog(this);
            }
        }
    }
    public void addSellLog(SellLog sellLog)
    {
        allSellLogs.put(sellLog.getLogId(),sellLog);
    }
    public void removeBuyLog(SellLog sellLog)
    {
        allSellLogs.remove(sellLog.getLogId(),sellLog);
    }
}
