package Client.Model;

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
    private boolean isExpired;

    public Auction(String auctionId, String startTime, String endTime, String sellerUsername, String maxSuggestedAmount,String sellerFirstName,String sellerLastName,boolean isExpired,String winner) {
        this.auctionId = auctionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sellerUsername = sellerUsername;
        this.maxSuggestedAmount = maxSuggestedAmount;
        this.sellerFirstName = sellerFirstName;
        this.sellerLastName = sellerLastName;
        this.isExpired = isExpired;
        this.winner = winner;
    }

    public boolean isExpired() {
        return isExpired;
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

    public void setExpired(String data) {
        if(data.equalsIgnoreCase("true"))
            isExpired = true;
        else
            isExpired = false;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setMaxSuggestedAmount(String maxSuggestedAmount) {
        this.maxSuggestedAmount = maxSuggestedAmount;
    }
}
