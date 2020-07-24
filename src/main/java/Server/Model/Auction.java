package Server.Model;

import Server.Controller.Control;
import Server.Controller.ControlCustomer;
import Server.DatabaseHandler;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Auction implements Serializable {

    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private String auctionId;
    private String auctionProduct;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double maxSuggestedAmount = 0;
    private String seller;
    private String auctionWinner;
    private HashMap<String, Double> customersSuggestionAmount = new HashMap<>();
    boolean sold = false;

    public Auction(String auctionId, Product auctionProduct, LocalDateTime startTime, LocalDateTime endTime, Seller seller){
        setAuctionId(auctionId);
        setAuctionProduct(auctionProduct);
        setStartTime(startTime);
        setEndTime(endTime);
        setSeller(seller);
        allAuctions.add(this);
        SaveData.saveData(this, "Auction:" + auctionId, SaveData.auctionFile);
    }

    private void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    private void setAuctionProduct(Product auctionProduct) {
        this.auctionProduct = auctionProduct.getProductId();
    }

    private void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    private void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    private void setSeller(Seller seller) {
        this.seller = seller.getUsername();
    }

    public String getAuctionWinner() {
        return auctionWinner;
    }

    public static ArrayList<Auction> getAllAuctions() {
        return allAuctions;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Product getAuctionProduct() {
        return Product.getAllProducts().get(auctionProduct);
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
        return (Seller)Account.getAllAccounts().get(seller);
    }

    public void addCustomersSuggestion(Customer customer, double amount) throws Exception{
        if(customer.getWalletBalance() - Control.getInstance().getLeastAmountWallet() < amount)
            throw new Exception("You don't have enough money in your wallet.");
        if(amount <= maxSuggestedAmount)
            throw new Exception("You should place a higher bid.");
        if(isExpired())
            throw new Exception("Auction has been expired.");
        customersSuggestionAmount.put(customer.getUsername(), amount);
        maxSuggestedAmount = amount;
        auctionWinner = customer.getUsername();
    }

    public boolean isExpired(){
        if(LocalDateTime.now().isAfter(endTime) && !sold){
            if(auctionWinner != null && !auctionWinner.equalsIgnoreCase("NULL")){
                Customer winner = (Customer) Account.getAllAccounts().get(auctionWinner);
                ControlCustomer.getInstance().purchaseAuction(winner,getAuctionProduct(),getSeller(),maxSuggestedAmount);
                sold = true;
            }
        }
        return LocalDateTime.now().isAfter(endTime);
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.auctionFile)));
        for (Object object : objects) {
            allAuctions.add((Auction)object);
        }
    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Auction> auctions = new ArrayList<>(DatabaseHandler.selectFromAuction());
        for (Auction auction : auctions) {
            allAuctions.add(auction);
        }
    }
}
