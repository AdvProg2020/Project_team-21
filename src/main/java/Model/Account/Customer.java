package Model.Account;

import Controller.Sort;
import Model.DiscountCode;
import Model.Log.BuyLog;
import Model.Log.Log;
import Model.Off;
import Model.SaveData;
import Model.ShoppingCart;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Account implements Comparable<Customer>{

    private static ArrayList<Customer> allCustomer = new ArrayList<>();
    private ShoppingCart shoppingCart;
    private HashMap<String, DiscountCode> discountCodes = new HashMap<>();
    private HashMap<DiscountCode,Integer> discountCodesUsed = new HashMap<>();
    public double balance;
    public ArrayList <BuyLog> buyLogs = new ArrayList<>();
    public HashMap<String , Off> offs = new HashMap<>();

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        addNewCustomer(this);
        setShoppingCart(new ShoppingCart(this));
        SaveData.saveData(this, getUsername(), SaveData.customerFile);
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

    public void addDiscountCode(DiscountCode discountCode)
    {
        discountCodes.put(discountCode.getDiscountId(),discountCode);
        discountCodesUsed.put(discountCode,0);
    }
    public void addDiscountUse(DiscountCode discountCode)
    {
        discountCodesUsed.put(discountCode,discountCodesUsed.get(discountCode)+1);
        if(discountCodesUsed.get(discountCode) >= discountCode.getDiscountNumberForEachUser())
        {
            removeDiscountCode(discountCode);
        }
    }

    public HashMap<String, DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        discountCodes.remove(discountCode.getDiscountId());
        discountCodesUsed.remove(discountCode);
    }

    public HashMap<String, Off> getOffs() {
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

    public double getBalance() {
        return balance;
    }

    @Override
    public String getType() {
        return "Customer";
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public int compareTo(Customer o) {
        return getUsername().compareTo(o.getUsername());
    }

    public static void sortAllCustomersById(){
        Customer.setAllCustomer(Sort.sortCustomerArrayList(getaAllCustomers()));
    }

    private static void setAllCustomer(ArrayList<Customer> allCustomer) {
        Customer.allCustomer = allCustomer;
    }

    private static ArrayList<Customer> getAllCustomer() {
        return allCustomer;
    }

    public void sortBuyLogsByLogId(){
        this.setBuyLogs(Sort.sortBuyLogArrayList(this.getBuyLogs()));
    }

    private void setBuyLogs(ArrayList<BuyLog> buyLogs) {
        this.buyLogs = buyLogs;
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.customerFile)));
        for (Object object : objects) {
            allCustomer.add((Customer) (object));

            getAllAccounts().put(((Account)object).getUsername() ,(Account)(object));
        }
    }
}
