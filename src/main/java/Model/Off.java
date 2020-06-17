package Model;

import Controller.Sort;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Off implements Comparable<Off>{

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
        SaveData.saveData(this, getOffId(), SaveData.offFile);
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

        File file = new File(off.getOffId()+".txt");
        if(file.delete()){
//            System.out.println("yes");
        } else {
//            System.out.println("hah");
        }
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

    @Override
    public int compareTo(Off o) {
        return (int)(getOffAmount()-o.getOffAmount());
    }

    private static void setAllOffs(HashMap<String, Off> allOffs) {
        Off.allOffs = allOffs;
    }

    public static void sortAllOffsByOffID(){
        Off.setAllOffs((HashMap<String, Off>) Sort.sortOffHashMap(getAllOffs()));
    }

    public static void sortAllOffsByOffAmount(){
        Off.setAllOffs(convertArrayListToHashMap(Sort.sortOffArrayListByAmount(new ArrayList<>(Off.getAllOffs().values()))));
    }

    private static HashMap<String, Off> convertArrayListToHashMap(ArrayList<Off> offs){
        HashMap<String, Off> offHashMap = new HashMap<>();
        for (Off off : offs) {
            offHashMap.put(off.getOffId(), off);
        }

        return offHashMap;
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.offFile)));
        for (Object object : objects) {
            allOffs.put(((Off)object).getOffId() ,(Off) (object));
        }
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
