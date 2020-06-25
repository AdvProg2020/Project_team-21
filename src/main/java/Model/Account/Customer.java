package Model.Account;

import Controller.Control;
import Controller.Sort;
import Model.DiscountCode;
import Model.Log.BuyLog;
import Model.Off;
import Model.SaveData;
import Model.ShoppingCart;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Account implements Comparable<Customer>{

    private static ArrayList<Customer> allCustomer = new ArrayList<>();
    private String shoppingCart;
    private ArrayList<String> discountCodes = new ArrayList<>();
    private HashMap<String,Integer> discountCodesUsed = new HashMap<>();
    private ArrayList<String> buyLogs = new ArrayList<>();
    public ArrayList<String> offs = new ArrayList<>();
    public double balance;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo) {
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        addNewCustomer(this);
        SaveData.saveData(this, getUsername(), SaveData.customerFile);
        ShoppingCart cart = new ShoppingCart(this, Control.getInstance().randomString(5));
        setShoppingCart(cart);
    }

    //    public static void rewriteFiles(){
//        for (Customer customer : Customer.getAllCustomer()) {
//            File file = new File(customer.getUsername()+".json");
//            file.delete();
//            SaveData.saveData(customer, customer.getUsername(), SaveData.customerFile);
//        }
//    }
    public static void rewriteFiles(){
        for (Customer customer : Customer.getAllCustomer()) {
            SaveData.saveDataRunning(customer, customer.getUsername(), SaveData.customerFile);
        }
    }

    public static void removeCustomer (Customer customer){
        allCustomer.remove(customer);

        File file = new File(customer.getUsername()+".json");
        file.delete();
    }

    public static ArrayList<Customer> getaAllCustomers() {
        return allCustomer;
    }

    public static void addNewCustomer(Customer customer){
        allCustomer.add(customer);
    }

    public ShoppingCart getShoppingCart() {
        ShoppingCart shoppingCart = null;
        for (ShoppingCart cart : ShoppingCart.getAllShoppingCarts()) {
            if(cart.getCartID().equals(this.shoppingCart))
                shoppingCart = cart;
        }
        return shoppingCart;
    }

    public ArrayList<BuyLog> getBuyLogs() {
        ArrayList<BuyLog> res = new ArrayList<>();
        for (String log : buyLogs) {
            BuyLog.getAllBuyLogs().get(log);
        }
        return res;
    }

    public void addDiscountCode(DiscountCode discountCode)
    {
        discountCodes.add(discountCode.getDiscountId());
        discountCodesUsed.put(discountCode.getDiscountId(),0);
    }
    public void addDiscountUse(DiscountCode discountCode)
    {
        discountCodesUsed.put(discountCode.getDiscountId(),discountCodesUsed.get(discountCode.getDiscountId())+1);
        if(discountCodesUsed.get(discountCode.getDiscountId()) >= discountCode.getDiscountNumberForEachUser())
        {
            removeDiscountCode(discountCode);
        }
    }

    public HashMap<String, DiscountCode> getDiscountCodes() {
        HashMap<String, DiscountCode> res = new HashMap<>();
        for (String code : discountCodes) {
            res.put(code,DiscountCode.getAllDiscountCodes().get(code));
        }
        return res;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        discountCodes.remove(discountCode.getDiscountId());
        discountCodesUsed.remove(discountCode.getDiscountId());
    }

    public HashMap<String, Off> getOffs() {
        HashMap<String,Off> res = new HashMap<>();
        for (String off : offs) {
            res.put(off,Off.getAllOffs().get(off));
        }
        return res;
    }

    public void addBuyLogs (BuyLog buyLog){

        buyLogs.add(buyLog.getLogId());
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart.getCartID();
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
        for (BuyLog log : buyLogs) {
            this.buyLogs.add(log.getLogId());
        }
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.customerFile)));
        for (Object object : objects) {
            allCustomer.add((Customer) (object));

            getAllAccounts().put(((Account)object).getUsername() ,(Account)(object));
        }
    }
}
