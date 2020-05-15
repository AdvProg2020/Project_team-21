package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {

    private static HashMap<String, DiscountCode> allDiscountCodes = new HashMap<>();
    private String discountId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double discountPercentage;
    private double maxDiscountAmount;
    private int discountNumberForEachUser;
    private HashMap<Account, Integer> discountUsers;

    // Initialization Block
    {
        discountUsers = new HashMap<>();
    }

    public DiscountCode(String discountId, LocalDateTime startTime, LocalDateTime endTime, double discountPercentage, double maxDiscountAmount, int discountNumberForEachUser){

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

    private void setDiscountNumberForEachUser(int discountNumberForEachUser) {
        this.discountNumberForEachUser = discountNumberForEachUser;
    }
}
