package Server.Model;

import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Auction {

    private static ArrayList<Auction> allAuctions = new ArrayList<>();
    private String auctionId;
    private String auctionProduct;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double maxSuggestedAmount;
    private String seller;
    private String auctionWinner = "NULL";
    private HashMap<String, Double> customersSuggestionAmount = new HashMap<>();

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

    public void setMaxSuggestedAmount(double maxSuggestedAmount) {
        this.maxSuggestedAmount = maxSuggestedAmount;
    }

    public void setAuctionWinner(Customer auctionWinner) {

        this.auctionWinner = auctionWinner.getUsername();
    }

    public Customer getAuctionWinner() {
        if(auctionWinner.equalsIgnoreCase("NULL"))
            return null;
        return (Customer)Account.getAllAccounts().get(auctionWinner);
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
        if(canCustomerGiveSuggestion(customer,amount))
            customersSuggestionAmount.put(customer.getUsername(), amount);
        else
            throw new Exception("You don't have enough money");
    }

    public boolean canCustomerChangeAmount(Customer customer, double newAmount){
        if(customersSuggestionAmount.get(customer.getUsername())<newAmount) {
            return true;
        }
        return false;
    }

     public boolean canCustomerGiveSuggestion(Customer customer,double amount){
        if(customer.getBalance() < amount)
            return false;
        return true;
     }

    public void changeCustomersSuggestion(Customer customer, double amount) throws Exception{
        if(!canCustomerChangeAmount(customer, amount))
            throw new Exception("Your new suggestion should be higher.");
        if(canCustomerGiveSuggestion(customer,amount))
            throw new Exception("You don't have enough credit.");
        else
            customersSuggestionAmount.replace(customer.getUsername(), amount);
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(endTime);
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.auctionFile)));
        for (Object object : objects) {
            allAuctions.add((Auction)object);
        }
    }
}
