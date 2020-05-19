package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Off {

    private static HashMap<String, Off> allOffs = new HashMap<>();
    public static ArrayList<Off> allOffsList = new ArrayList<>();

    private String offId;
    private ArrayList<Product> productsList;
    private OffState offState;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double offAmount;
    private DisAndOffStatus disAndOffStatus;

    // Initialization Block
    {
        productsList = new ArrayList<>();
    }

    public Off(String offId, ArrayList<Product> productsList, LocalDateTime startTime, LocalDateTime endTime, double offAmount){
        setOffId(offId);
        setProductsList(productsList);
        setStartTime(startTime);
        setEndTime(endTime);
        setOffAmount(offAmount);
    }

    private void setOffId(String offId) {
        this.offId = offId;
    }

    public static void addOff(Off off)
    {
        allOffs.put(off.getOffId(),off);
    }
    public static void removeOff(Off off)
    {
        allOffs.remove(off.getOffId());
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
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

    public static HashMap<String, Off> getAllOffs() {
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

    public OffState getOffState() {
        return offState;
    }

    public void addProduct(Product product){
        productsList.add(product);
    }

    public void removeProduct(Product product){
        productsList.remove(product);
    }

    public DisAndOffStatus getDisAndOffStatus (){
        if (startTime.compareTo(LocalDateTime.now())>0){
            return DisAndOffStatus.Not_Started;
        }
        if (endTime.compareTo(LocalDateTime.now())>=0){
            return DisAndOffStatus.Expired;
        }
        return DisAndOffStatus.Active;
    }

    public void removeOff(){
        allOffsList.remove(this);

    }




    public void removeOffFromProducts(){
        for (Product product : productsList){
            product.removeOff(this);
        }
    }

}
