package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Account {

    private ArrayList<Customer> allCustomer = new ArrayList<Customer>();
    private ShoppingCart shoppingCart;
    public ArrayList <BuyLog> buyLogs = new ArrayList<BuyLog>();
    public HashMap<Off , Integer> offs = new HashMap<>();
    public int balance;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, ArrayList<DiscountCode> discountList, double credit) {
        super(username, firstName, lastName, email, phoneNumber, password, discountList, credit);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }

    public HashMap<Off, Integer> getOffs() {
        return offs;
    }

    public void addBuyLogs (BuyLog buyLog){
        buyLogs.add(buyLog);
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

//    public void addOffs (Off off){
//
////    }
//    public void removeOffs (Off off){
//
//    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
