package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Account implements Comparable<Customer>{

    private static ArrayList<Customer> allCustomer = new ArrayList<Customer>();
    private ShoppingCart shoppingCart;
    public ArrayList <BuyLog> buyLogs = new ArrayList<BuyLog>();
    public HashMap<Off , Integer> offs = new HashMap<>();
    public int balance;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        addNewCustomer(this);
    }

    public static void removeCustomer (Customer customer){
        allCustomer.remove(customer);
    }

    public static ArrayList<Customer> getaAllCustomers() {
        return allCustomer;
    }

    public static void addNewCustomer(Customer customer){
        allCustomer.add(customer);
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

    @Override
    public String getType() {
        return "Customer";
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int compareTo(Customer customer) {
        return getUsername().compareTo(customer.getUsername());
    }

}
