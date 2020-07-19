package Client.Model;

public class DiscountCode {
    private String discountId;
    private String discountPercentage;
    private String discountNumberForEachUser;
    private String maxDiscountAmount;

    public DiscountCode(String discountId, String discountPercentage, String discountNumberForEachUser, String maxDiscountAmount) {
        this.discountId = discountId;
        this.discountPercentage = discountPercentage;
        this.discountNumberForEachUser = discountNumberForEachUser;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public String getDiscountId() {
        return discountId;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public String getDiscountNumberForEachUser() {
        return discountNumberForEachUser;
    }

    public String getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
}
