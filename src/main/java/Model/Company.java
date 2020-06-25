package Model;

import Model.Account.Account;
import Model.Account.Seller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Company {
    private static HashMap<String,Company> allCompanies = new HashMap<>();
    private String name;
    private String location;
    private ArrayList<String> allSellers = new ArrayList<>();
    private ArrayList<String> allProducts = new ArrayList<>();


    public Company(String name, String location) {
        this.name = name;
        this.location = location;
        allCompanies.put(name,this);
        SaveData.saveData(this, getName(), SaveData.companyFile);
    }
    public static void rewriteFiles(){
        for (String s : allCompanies.keySet()) {
            SaveData.saveDataRunning(allCompanies.get(s), s, SaveData.companyFile);
        }
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
        allProducts.add(product.getProductId());
    }

    public ArrayList<String> getAllProducts() {
        return allProducts;
    }

    public ArrayList<String> getAllSellers() {
        return allSellers;
    }

    public void addSeller(Seller seller)
    {
        allSellers.add(seller.getUsername());
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.companyFile)));
        for (Object object : objects) {
            allCompanies.put(((Company)object).getName() ,(Company)(object));
        }
    }
}
