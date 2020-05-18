package Model;

import Model.Account.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Company {
    private static HashMap<String,Company> allCompanies = new HashMap<>();
    private String name;
    private String location;
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();

    public Company(String name, String location) {
        this.name = name;
        this.location = location;
        allCompanies.put(name,this);
    }

    public static HashMap<String, Company> getAllCompanies() {
        return allCompanies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void addProduct(Product product)
    {
        allProducts.add(product);
    }

    public void addSeller(Seller seller)
    {
        allSellers.add(seller);
    }
}
