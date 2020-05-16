package Model.Log;

import Model.Product;

import java.util.ArrayList;
import java.util.Date;

public class BuyLog extends Log{
    public BuyLog(String logId, Date date, double totalDiscountAmount, double totalAmount, ArrayList<Product> allProducts, String sellerUserName, String receiverUserName) {
        super(logId, date, totalDiscountAmount, totalAmount, allProducts, sellerUserName, receiverUserName);
    }
}
