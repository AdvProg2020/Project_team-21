package Client.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Off {
    private double offAmount;
    private String startTime;
    private String endTime;
    private ArrayList<Product> productsList = new ArrayList<>();
    private String offId;

    public Off(double offAmount, String startTime, String endTime, String offId) {
        this.offAmount = offAmount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.offId = offId;
    }

    public double getOffAmount() {
        return offAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public String getOffId() {
        return offId;
    }

    public void addProduct(Product product){
        productsList.add(product);
    }
}
