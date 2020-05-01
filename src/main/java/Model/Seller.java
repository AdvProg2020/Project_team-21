package Model;

import java.util.ArrayList;

public class Seller extends Account {
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private String companyName;
    private ArrayList<Product> allProducts = new ArrayList<>();
    private ArrayList<Off> allOffs = new ArrayList<>();
    private ArrayList<SellLog> sellLogs = new ArrayList<>();


    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, ArrayList<DiscountCode> discountList, double credit, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, discountList, credit);
        this.companyName = companyName;
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
}
