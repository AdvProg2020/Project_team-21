package Model;

import Controller.Sort;

import Model.Account.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DiscountCode {

    private static HashMap<String, DiscountCode> allDiscountCodes = new HashMap<>();
    private String discountId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double discountPercentage;
    private double maxDiscountAmount;
    private int discountNumberForEachUser;
    private HashMap<String, Customer> discountOwners;
    // Initialization Block
    {
        discountOwners = new HashMap<>();
    }

    public DiscountCode(String discountId, LocalDateTime startTime, LocalDateTime endTime, double discountPercentage, double maxDiscountAmount, int discountNumberForEachUser,HashMap <String,Customer> codeOwners)
    {
        for (String s : codeOwners.keySet())
        {
            discountOwners.put(s,codeOwners.get(s));
            codeOwners.get(s).addDiscountCode(this);
        }
        setDiscountId(discountId);
        setStartTime(startTime);
        setEndTime(endTime);
        setDiscountPercentage(discountPercentage);
        setMaxDiscountAmount(maxDiscountAmount);
        setDiscountNumberForEachUser(discountNumberForEachUser);
        allDiscountCodes.put(discountId,this);
    }

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
        discountOwners.put(customer.getUsername(),customer);
        customer.addDiscountCode(this);
    }
    public void removeDiscountOwner(Customer customer)
    {
        discountOwners.remove(customer.getUsername(),customer);
        customer.removeDiscountCode(this);
    }

    public HashMap<String, Customer> getDiscountOwners() {
        return discountOwners;
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


}
