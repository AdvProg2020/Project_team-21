package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Off implements Comparable<Off>{

    private static ArrayList<Off> allOffs = new ArrayList<>();
    private String offId;
    private ArrayList<Product> productsList;
    private OffState offState;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double offAmount;

    // Initialization Block
    {
        productsList = new ArrayList<>();
    }

    public Off(String offId, ArrayList<Product> productsList, OffState offState, LocalDateTime startTime, LocalDateTime endTime, double offAmount){
        setOffId(offId);
        setProductsList(productsList);
        setOffState(offState);
        setStartTime(startTime);
        setEndTime(endTime);
        setOffAmount(offAmount);
        allOffs.add(this);
    }

    private void setOffId(String offId) {
        this.offId = offId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setProductsList(ArrayList<Product> productsList) {
        this.productsList.addAll(productsList);
    }

    public void setOffAmount(double offAmount) {
        this.offAmount = offAmount;
    }

    public void setOffState(OffState offState) {
        this.offState = offState;
    }

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public String getOffId() {
        return offId;
    }

    public double getOffAmount() {
        return offAmount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean doesProductExistWithId(String id){
        for (Product product : productsList) {
            if(product.getProductId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product){
        productsList.add(product);
    }

    public void removeProduct(Product product){
        productsList.remove(product);
    }

    @Override
    public int compareTo(Off o) {
        return getOffId().compareTo(o.getOffId());
    }
}
