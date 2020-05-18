package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Customer extends Account {

    private ArrayList<Customer> allCustomer = new ArrayList<Customer>();
    private ShoppingCart shoppingCart;
    private ArrayList <BuyLog> buyLogs = new ArrayList<BuyLog>();
    private Set<String> buyLogIds;
    private HashMap<String , Integer> discountIds = new HashMap<>();
    public double balance;
    private static int lasNum =1;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password,
                    double credit, double balance) {
        super(username, firstName, lastName, email, phoneNumber, password, credit);
        this.balance=balance;


    }

    public static Customer getCustomerById(String id , boolean... suspense){
        return (Customer) getAccountById(id,suspense);
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public ArrayList<BuyLog> getBuyLogs() {
        return buyLogs;
    }


    public void addBuyLogs (BuyLog buyLog){
        buyLogs.add(buyLog);
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String Type(){
        return "Customer";
    }

    @Override
    public void Initialize() {
        if (accountID==null)
            accountID=Methods.generateId(getClass().getSimpleName(), lasNum);
        allAccountsMap.put(accountID,this);
        lasNum++;
        buyLogIds=new HashSet<>();
        if (!suspended){
            discountIds =new HashMap<>();
        }
    }

    @Override
    public void suspend() {
        //TODO
    }

    public void changeBalance(double changeAmount){
        balance+=changeAmount;
    }



}
