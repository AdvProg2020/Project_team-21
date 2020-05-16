package Model;

import Model.Account.Seller;

import java.util.ArrayList;

public class Company {
    private static ArrayList<Company> allCompanies = new ArrayList<>();
    private String name;
    private String location;
    private ArrayList<Seller> allSellers = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();

    public Company(String name, String location) {
        this.name = name;
        this.location = location;
        allCompanies.add(this);
    }

    public static ArrayList<Company> getAllCompanies() {
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
