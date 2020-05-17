package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Seller extends Account {
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private Set<String> subProductsId;
    private Set<String> saleIds;
    private Set<String> sellLogsIds;
    private static int lastNum=1;
    private String companyName;
    private double balance;


    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Off> allOffs = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();


    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password,
                  double credit,double balance, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, credit);
        this.companyName = companyName;
        this.balance=balance;

    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public ArrayList<SellLog> getSellLogs() {
        return sellLogs;
    }

    public void addProduct (Product product){
        allProducts.add(product);
    }

    public void removeProduct(Product product){
        allProducts.remove(product);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void addOffs (Off off){
        allOffs.add(off);
    }

    public void setSellLogs(ArrayList<SellLog> sellLogs) {
        this.sellLogs = sellLogs;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addSellLog (SellLog sellLog){sellLogs.add(sellLog);}

    public static Seller getSellerById(String id,boolean... suspense){
        return (Seller) getAccountById(id,suspense);
    }

    @Override
    public void Initialize() {
        if (accountID==null)
            accountID=Methods.generateId(getClass().getSimpleName(),lastNum);
        allAccountsMap.put(accountID,this);
        lastNum++;
        sellLogsIds=new HashSet<>();
        if (!suspended){
            subProductsId= new HashSet<>();
            saleIds= new HashSet<>();
        }
    }

    @Override
    public void suspend() {

    }

    @Override
    public String Type(){
        return "Seller";
    }
}
