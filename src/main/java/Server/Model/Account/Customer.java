package Server.Model.Account;

import Server.Controller.Control;
import Server.Controller.Sort;
import Server.Model.*;
import Server.Model.Log.BuyLog;
import Server.ServerCenter;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import Server.DatabaseHandler;

public class Customer extends Account implements Comparable<Customer>, Serializable {

    private static ArrayList<Customer> allCustomer = new ArrayList<>();
    private String shoppingCart;
    private ArrayList<String> discountCodes = new ArrayList<>();
    private HashMap<String,Integer> discountCodesUsed = new HashMap<>();
    private ArrayList<String> buyLogs = new ArrayList<>();
    public ArrayList<String> offs = new ArrayList<>();
    private ArrayList<String> files = new ArrayList<>();
    private String bankAccountID;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo) {
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        addNewCustomer(this);
//        SaveData.saveData(this, getUsername(), SaveData.customerFile);
        String cartID = Control.getInstance().randomString(5);
        new ShoppingCart(this, cartID);
        setShoppingCart(cartID);
        new Wallet(Control.getInstance().getLeastAmountWallet(),username);
        bankAccountID = ServerCenter.getInstance().createAccountBank(firstName,lastName,username,password,password);
    }

    //    public static void rewriteFiles(){
//        for (Customer customer : Customer.getAllCustomer()) {
//            File file = new File(customer.getUsername()+".json");
//            file.delete();
//            SaveData.saveData(customer, customer.getUsername(), SaveData.customerFile);
//        }
//    }
//    public static void rewriteFiles(){
//        for (Customer customer : Customer.getAllCustomer()) {
//            SaveData.saveDataRunning(customer, customer.getUsername(), SaveData.customerFile);
//        }
//    }

    public boolean hasBought(Product product){
        if(product.getBuyers().contains(this))
            return true;
        return false;
    }

    public static void removeCustomer (Customer customer){
        allCustomer.remove(customer);
//        File file = new File("Database/" + customer.getUsername()+".json");
//        file.delete();
    }
    public void addBuyLogs (BuyLog buyLog){
        buyLogs.add(buyLog.getLogId());
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
            if(cart.getCustomer().equals(this)){
                shoppingCart = cart;
                break;
            }
        }
        return shoppingCart;
    }

    public ArrayList<BuyLog> getBuyLogs() {
        ArrayList<BuyLog> res = new ArrayList<>();
        for (String log : buyLogs) {
            res.add(BuyLog.getAllBuyLogs().get(log));
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
            discountCode.removeDiscountOwner(this);
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



    public void setShoppingCart(String id) {
        this.shoppingCart = id;
    }


    public double getAccountBalance() {
        double result = Double.parseDouble(ServerCenter.getInstance().getBalanceBank(getUsername(),getPassword()));
        return result;
    }

    @Override
    public String getType() {
        return "Customer";
    }

    public String payWithBankAccount(double amount) throws Exception{
        String receiptID = ServerCenter.getInstance().createReceiptBank("move",Double.toString(amount),getBankAccountID(),"1","Buying from payment page",
                getUsername(),getPassword());
        String result = ServerCenter.getInstance().payBank(receiptID);
        if(!result.equalsIgnoreCase("done successfully"))
            throw new Exception(result);
        return "DONE";
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

//    public void sortBuyLogsByLogId(){
//        this.setBuyLogs(Sort.sortBuyLogArrayList(this.getBuyLogs()));
//    }

    private void setBuyLogs(ArrayList<BuyLog> buyLogs) {
        for (BuyLog log : buyLogs) {
            this.buyLogs.add(log.getLogId());
        }
    }

//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.customerFile)));
//        for (Object object : objects) {
//            allCustomer.add((Customer) (object));
//            getAllAccounts().put(((Account)object).getUsername() ,(Account)(object));
//        }
//    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Customer> customers = new ArrayList<>(DatabaseHandler.selectFromCustomer());
        for (Customer customer : customers) {
            allCustomer.add(customer);
            getAllAccounts().put(customer.getUsername(), customer);
        }
    }

    public Wallet getWallet() {
        Wallet wallet = null;
        for (Wallet allWallet : Wallet.getAllWallets()) {
            if(allWallet.getAccount().equals(this.getUsername())){
                wallet = allWallet;
                break;
            }
        }
        return wallet;
    }

    public double getWalletBalance(){
        System.out.println("dar wallet " + getWallet().getMoney());
        return getWallet().getMoney();
    }

    public String getBankAccountID() {
        return bankAccountID;
    }

    public ArrayList<String> getFiles() {
        return files;
    }
    public void addFile(String file){
        files.add(file);
//        rewriteFiles();
//        Account.rewriteFiles();
    }

    public static boolean customerExists(String username){
        for (Customer customer : allCustomer) {
            if(customer.getUsername().equals(username))
                return true;
        }
        return false;
    }
}
