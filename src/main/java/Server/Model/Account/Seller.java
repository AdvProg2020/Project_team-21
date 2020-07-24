package Server.Model.Account;

import Server.Controller.Sort;
import Server.DatabaseHandler;
import Server.Model.*;
//import Server.Model.BankPrime.BankAccount;
import Server.Model.Log.SellLog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Seller extends Account implements Comparable<Seller>, Serializable {

    private static ArrayList<Seller> allSellers = new ArrayList<>();

    private String company;
    private ArrayList<String> allProducts = new ArrayList<>();
    private ArrayList<String> allOffs = new ArrayList<>();
    private ArrayList<String> sellLogs = new ArrayList<>();
    private Wallet wallet;
    private String requestID;


    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, Company company,String photo) {
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        if(company != null)
            this.company = company.getName();
        //TODO ASK MOHAMMAD : in chera credit nadare?
//        this.wallet = new Wallet(this , 0);
//        this.bankAccount = new BankAccount(firstName , lastName , username , password);
    }

//    public static void rewriteFiles(){
//        for (Seller seller : Seller.getAllSeller()) {
//            SaveData.saveDataRunning(seller, seller.getUsername(), SaveData.sellerFile);
//        }
//    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public static void removeSeller (Seller seller){
        allSellers.remove(seller);
//        File file = new File("Database/" + seller.getUsername()+".json");
//        file.delete();
    }
    public void removeOff(Off off)
    {
        allOffs.remove(off.getOffId());
    }

    public static ArrayList<Seller> getAllSeller() {
        return allSellers;
    }

    public static void addNewSeller (Seller seller){
        Account.addAccount(seller);
        allSellers.add(seller);
//        SaveData.saveData(seller, seller.getUsername(), SaveData.sellerFile);
    }

    public Company getCompany() {
        return Company.getAllCompanies().get(company);
    }

    public String getCompanyName(){
        if(getCompany() != null)
            return getCompany().getName();
        return "Not Set";
    }

    public String getCompanyAddress(){
        if(getCompany() != null)
            return getCompany().getLocation();
        return "Not Set";
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> res = new ArrayList<>();
        for (String product : allProducts) {
            res.add(Product.getAllProducts().get(product));
        }
        return res;
    }

    public ArrayList<Off> getAllOffs() {
        ArrayList<Off> res = new ArrayList<>();
        for (String off : allOffs) {
            res.add(Off.getAllOffs().get(off));
        }
        return res;
    }

    public ArrayList<SellLog> getSellLogs() {
        ArrayList<SellLog> res = new ArrayList<>();
        for (String sellLog : sellLogs) {
            res.add(SellLog.getAllSellLogs().get(sellLog));
        }
        return res;
    }

    public void addProduct (Product product){
        allProducts.add(product.getProductId());
    }

    public void removeProduct(Product product){
        allProducts.remove(product.getProductId());
    }

    public void setCompany(Company company)
    {
        this.company = company.getName();
    }

    public void addSellLog(SellLog sellLog)
    {
        sellLogs.add(sellLog.getLogId());
    }
    public void addOffs (Off off){
        allOffs.add(off.getOffId());
    }

    public static Seller getSellerWithUsername(String username)throws Exception{
        for (Seller seller : allSellers){
            if (seller.getUsername().equals(username))
                return seller;
        }
        throw new Exception("There is no seller with user name : " + username + "\n");
    }

    @Override
    public String getType() {
        return "Seller";
    }

    public void setSellLogs(ArrayList<SellLog> sellLogs) {
        for (SellLog sellLog : sellLogs) {
            this.sellLogs.add(sellLog.getLogId());
        }
    }

    @Override
    public int compareTo(Seller o) {
        return getUsername().compareTo(o.getUsername());
    }

    public void sortSellLogsByLogId(){
        this.setSellLogs(Sort.sortSellLogArrayList(this.getSellLogs()));
    }

//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.sellerFile)));
//        for (Object object : objects) {
//            allSellers.add((Seller) (object));
//
//            getAllAccounts().put(((Account)object).getUsername() ,(Account)(object));
//        }
//    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Seller> sellers = new ArrayList<>(DatabaseHandler.selectFromSeller());
        for (Seller seller : sellers) {
            allSellers.add(seller);
            getAllAccounts().put(seller.getUsername(), seller);
        }
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
