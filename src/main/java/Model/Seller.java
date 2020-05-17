package Model;

import java.util.ArrayList;

public class Seller extends Account implements Comparable<Seller>{

    private static ArrayList<Seller> allSellers = new ArrayList<>();
    private String companyName;
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Off> allOffs = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();


    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password);
        this.companyName = companyName;
        addNewSeller(this);
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

    @Override
    public String getType() {
        return "Seller";
    }

    public void setSellLogs(ArrayList<SellLog> sellLogs) {
        this.sellLogs = sellLogs;
    }

    @Override
    public int compareTo(Seller o) {
        return getUsername().compareTo(o.getUsername());
    }
}
