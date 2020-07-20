package Client.Model;

import java.util.HashMap;

public class Auction {
    private String auctionId;
    private String startTime;
    private String endTime;
    private Product auctionProduct;
    private String sellerUsername;
    private String sellerFirstName;
    private String sellerLastName;
    private String winner;
    private String maxSuggestedAmount;
    private HashMap<String, String> customersSuggestionAmount = new HashMap<>();

    public Auction(String auctionId, String startTime, String endTime, String sellerUsername, String maxSuggestedAmount,String sellerFirstName,String sellerLastName) {
        this.auctionId = auctionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sellerUsername = sellerUsername;
        this.maxSuggestedAmount = maxSuggestedAmount;
        this.sellerFirstName = sellerFirstName;
        this.sellerLastName = sellerLastName;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public String getWinner() {
        return winner;
    }

    public String getMaxSuggestedAmount() {
        return maxSuggestedAmount;
    }

    public HashMap<String, String> getCustomersSuggestionAmount() {
        return customersSuggestionAmount;
    }

    public void setAuctionProduct(Product auctionProduct) {
        this.auctionProduct = auctionProduct;
    }

    public Product getAuctionProduct() {
        return auctionProduct;
    }

    public String getSellerFirstName() {
        return sellerFirstName;
    }

    public String getSellerLastName() {
        return sellerLastName;
    }
}
