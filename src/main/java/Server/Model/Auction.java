package Server.Model;

import Server.Model.Account.Customer;
import Server.Model.Account.Seller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Auction {

    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private String auctionId;
    private Product auctionProduct;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double maxSuggestedAmount;
    private Seller seller;
    private Customer auctionWinner;
    private HashMap<Customer, Double> customersSuggestionAmount = new HashMap<>();

    public Auction(String auctionId, Product auctionProduct, LocalDateTime startTime, LocalDateTime endTime, Seller seller){
        setAuctionId(auctionId);
        setAuctionProduct(auctionProduct);
        setStartTime(startTime);
        setEndTime(endTime);
        setSeller(seller);
        allAuctions.add(this);
    }

    private void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    private void setAuctionProduct(Product auctionProduct) {
        this.auctionProduct = auctionProduct;
    }

    private void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    private void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    private void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setMaxSuggestedAmount(double maxSuggestedAmount) {
        this.maxSuggestedAmount = maxSuggestedAmount;
    }

    public void setAuctionWinner(Customer auctionWinner) {
        this.auctionWinner = auctionWinner;
    }

    public Customer getAuctionWinner() {
        return auctionWinner;
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Product getAuctionProduct() {
        return auctionProduct;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getMaxSuggestedAmount() {
        return maxSuggestedAmount;
    }

    public Seller getSeller() {
        return seller;
    }

    public void addCustomersSuggestion(Customer customer, double amount){
        customersSuggestionAmount.put(customer, amount);
    }

    public boolean canCustomerChangeAmount(Customer customer, double newAmount){
        if(customersSuggestionAmount.get(customer)<newAmount) {
            return true;
        }
        return false;
    }

    public void changeCustomersSuggestion(Customer customer, double amount){
        if(canCustomerChangeAmount(customer, amount)){
            customersSuggestionAmount.replace(customer, amount);
        }
    }
    public boolean isExpire(){
        return LocalDateTime.now().isAfter(endTime);
    }
}
