package Server.Model;

import Server.Controller.Sort;

import Server.DatabaseHandler;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode implements Serializable {

    private static HashMap<String, DiscountCode> allDiscountCodes = new HashMap<>();
    private String discountId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double discountPercentage;
    private double maxDiscountAmount;
    private int discountNumberForEachUser;
    private ArrayList<String> discountOwners = new ArrayList<>();

    public DiscountCode(String discountId, LocalDateTime startTime, LocalDateTime endTime, double discountPercentage, double maxDiscountAmount, int discountNumberForEachUser,HashMap <String,Customer> codeOwners)
    {
        for (String s : codeOwners.keySet())
        {
            discountOwners.add(s);
            codeOwners.get(s).addDiscountCode(this);
        }
        setDiscountId(discountId);
        setStartTime(startTime);
        setEndTime(endTime);
        setDiscountPercentage(discountPercentage);
        setMaxDiscountAmount(maxDiscountAmount);
        setDiscountNumberForEachUser(discountNumberForEachUser);
        allDiscountCodes.put(discountId,this);
//        SaveData.saveData(this, getDiscountId(), SaveData.discountCodeFile);
    }
//    public static void rewriteFiles(){
//        for (String s : allDiscountCodes.keySet()) {
//            SaveData.saveDataRunning(allDiscountCodes.get(s), s, SaveData.discountCodeFile);
//        }
//    }


    public static HashMap<String, DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public String getDiscountId() {
        return discountId;
    }

    public int getDiscountNumberForEachUser() {
        return discountNumberForEachUser;
    }

    public static void setAllDiscountCodes(HashMap<String, DiscountCode> allDiscountCodes) {
        DiscountCode.allDiscountCodes = allDiscountCodes;
    }

    public void addDiscountOwner(Customer customer)
    {
        discountOwners.add(customer.getUsername());
        customer.addDiscountCode(this);
    }
    public void removeDiscountOwner(Customer customer)
    {
        discountOwners.remove(customer.getUsername());
        customer.removeDiscountCode(this);
    }
    public static void removeDiscountCode (String code){
        allDiscountCodes.remove(code);

//        File file = new File(code+".json");
//        file.delete();
    }

    public HashMap<String, Customer> getDiscountOwners() {
        HashMap<String,Customer> res = new HashMap<>();
        for (String owner : discountOwners) {
            res.put(owner,(Customer)Account.getAllAccounts().get(owner));
        }
        return res;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    private void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public void setDiscountNumberForEachUser(int discountNumberForEachUser) {
        this.discountNumberForEachUser = discountNumberForEachUser;
    }

    public static void sortAllDiscountCodesByDiscountID(){
        DiscountCode.setAllDiscountCodes((HashMap<String, DiscountCode>) Sort.sortDiscountCodeHashMap(getAllDiscountCodes()));
    }

    public static void sortAllDiscountCodesByDiscountPercentage(){
        DiscountCode.setAllDiscountCodes(convertArrayListToHashMap(Sort.sortDiscountCodesByDiscountPercentage(new ArrayList<>(DiscountCode.getAllDiscountCodes().values()))));
    }

    private static HashMap<String, DiscountCode> convertArrayListToHashMap(ArrayList<DiscountCode> discountCodes){
        HashMap<String, DiscountCode> discountCodeHashMap = new HashMap<>();
        for (DiscountCode discountCode : discountCodes) {
            discountCodeHashMap.put(discountCode.getDiscountId(), discountCode);
        }
        return discountCodeHashMap;
    }

//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.discountCodeFile)));
//        for (Object object : objects) {
//            allDiscountCodes.put(((DiscountCode)object).getDiscountId() ,(DiscountCode) (object));
//        }
//    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<DiscountCode> discountCodes  = new ArrayList<>(DatabaseHandler.selectFromDiscountCode());
        for (DiscountCode discountCode : discountCodes) {
            allDiscountCodes.put(discountCode.getDiscountId(), discountCode);
        }
    }

}
