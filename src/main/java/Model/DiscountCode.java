package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DiscountCode {

    private static ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();
    private String discountId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int discountPercentage;
    private double maxDiscountAmount;
    private int discountNumberForEachUser;
    private HashMap<Account, Integer> discountUsers;

    // Initialization Block
    {
        discountUsers = new HashMap<>();
    }

    public DiscountCode(String discountId, LocalDateTime startTime, LocalDateTime endTime, int discountPercentage, double maxDiscountAmount, int discountNumberForEachUser){
        this.discountId = discountId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercentage = discountPercentage;
        this.maxDiscountAmount = maxDiscountAmount;
        this.discountNumberForEachUser = discountNumberForEachUser;
    }

    public String getDiscountId() {
        return discountId;
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

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
}
