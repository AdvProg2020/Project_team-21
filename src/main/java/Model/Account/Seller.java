package Model.Account;

import Model.Company;
import Model.Off;
import Model.Product;
import Model.Log.SellLog;

import java.util.ArrayList;

public class Seller extends Account {
    private static ArrayList<Seller> allSellers = new ArrayList<>();
    private Company company;
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Off> allOffs = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();


    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, Company company) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.company = company;
    }

    public static void removeSeller (Seller seller){
        allSellers.remove(seller);
    }

    public static ArrayList<Seller> getAllSeller() {
        return allSellers;
    }

    public static void addNewSeller (Seller seller){
        allSellers.add(seller);
    }

    public Company getCompany() {
        return company;
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

    public void setCompany(Company company) {
        this.company = company;
    }

    public void addOffs (Off off){
        allOffs.add(off);
    }

    @Override
    public String getType() {
        return "Seller";
    }

    public void setSellLogs(ArrayList<SellLog> sellLogs) {
        this.sellLogs = sellLogs;
    }
}
