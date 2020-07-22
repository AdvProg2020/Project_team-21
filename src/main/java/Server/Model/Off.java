package Server.Model;

import Server.Controller.Sort;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Off implements Comparable<Off>{

    private static HashMap<String, Off> allOffs = new HashMap<>();
    public static ArrayList<Off> allOffsList = new ArrayList<>();
    private String offId;
    private ArrayList<String> productsList;
    private OffState offState;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double offAmount;
    private DisAndOffStatus disAndOffStatus;
    private String requestID;

    // Initialization Block
    {
        productsList = new ArrayList<>();
    }

    public Off(String offId, ArrayList<Product> productsList, LocalDateTime startTime, LocalDateTime endTime, double offAmount){
        setOffId(offId);
        if(productsList != null)
            setProductsList(productsList);
        setStartTime(startTime);
        setEndTime(endTime);
        setOffAmount(offAmount);
    }

    public static void rewriteFiles(){
        for (String s : allOffs.keySet()) {
            SaveData.saveDataRunning(allOffs.get(s), s, SaveData.offFile);
        }
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    private void setOffId(String offId) {
        this.offId = offId;
    }

    public static void addOff(Off off)
    {
        allOffs.put(off.getOffId(),off);
        allOffsList.add(off);
        SaveData.saveData(off, off.getOffId(), SaveData.offFile);
    }
    public static void removeOff(Off off)
    {
        allOffs.remove(off.getOffId());
        allOffsList.remove(off);
        File file = new File("Database/" + off.getOffId()+".json");
        file.delete();
    }

    public ArrayList<Product> getProductsList() {
        ArrayList<Product> res = new ArrayList<>();
        for (String s : productsList) {
            res.add(Product.getAllProducts().get(s));
        }
        return res;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setProductsList(ArrayList<Product> productsList) {
        for (Product product : productsList) {
            this.productsList.add(product.getProductId());
        }
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
        for (Product product : getProductsList()) {
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
        productsList.add(product.getProductId());
    }

    public void removeProduct(Product product){
        productsList.remove(product.getProductId());
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
            allOffsList.add((Off) (object));
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
        allOffs.remove(this.getOffId());
    }




    public void removeOffFromProducts(){
        for (Product product : getProductsList()){
            product.removeOff(this);
        }
    }

}
