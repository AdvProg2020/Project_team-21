package Controller;

import Model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.Request;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class DataClasses {

    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private static ArrayList<Category> allCategories = new ArrayList<>();

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static Account getAccountByUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username))
                return account;
        }
        return null;
    }

    public static Product getProductByID(String productID) {
        for (Product product : allProducts) {
            if (product.DoesProductExistWithId(productID))
                return product;
        }
        return null;
    }

    public static Category getCategoryByName(String categoryname) {
        for (Category category : allCategories) {
            if (category.getName().equals(categoryname))
                return category;
        }
        return null;
    }

    public static void addAllAccount(Account account) {
        allAccounts.add(account);
    }
    public static void addAllProduct(Product product){
        allProducts.add(product);
    }
    public static void addAllCategory (Category category){
        allCategories.add(category);
    }


    public static void removeAccount(Account account) {
        allAccounts.remove(account);
    }

    public static void removeProduct(Product product) {
        allProducts.remove(product);
    }

    public static void removeCategory(Category category){
        allCategories.remove(category);
    }

    public static void writeDataOnFile(){
        writeAccountsOnFile();
        writeProductsOnFile();
    }

    private static void writeProductsOnFile(){
        File file = new File("Data\\Products.json");
        file.getParentFile().mkdirs();
        FileWriter fileWriter=null;
        try {
            fileWriter= new FileWriter(file);
            Gson gson = new Gson();
            String json = gson.toJson(allProducts);
            fileWriter.write(json);
            fileWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void writeAccountsOnFile(){
        ArrayList<Account> sellers = new ArrayList<>();
        ArrayList<Account> managers = new ArrayList<>();
        ArrayList<Account> customers = new ArrayList<>();
        for (Account account : allAccounts){
            if(account instanceof Manager){
                managers.add(account);
            }
            else if (account instanceof Seller){
                sellers.add(account);
            }
            else if (account instanceof Customer){
                customers.add(account);
            }
        }
        writeArrayAccountOnFile(managers, "Managers");
        writeArrayAccountOnFile(customers,"Customers");
        writeArrayAccountOnFile(sellers,"Sellers");
    }

    private static void writeArrayAccountOnFile(ArrayList<Account> arr, String name) {
        File file = new File("Data\\" + name + ".json");
        file.getParentFile().mkdirs();
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
            Gson gson = new Gson();
            String json = gson.toJson(arr);
            fileWriter.write(json);
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readArrayOfProductFromFile(String place) {
        Gson gson = new Gson();
        File file = new File("Data\\"+place+".json");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
        allProducts = gson.fromJson(br, type);
    }

    private static void readArrayOfAccountFromFile(String place) {
        Gson gson = new Gson();
        File file = new File("Data\\"+place+".json");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<Account> arr = new ArrayList<>();
        Type type ;
        if (place.equals("Admins")) {
            type = new TypeToken<ArrayList<Manager>>(){}.getType();
        }
        else if (place.equals("Buyers")){
            type = new TypeToken<ArrayList<Customer>>(){}.getType();
        }
        else{
            type = new TypeToken<ArrayList<Seller>>(){}.getType();
        }
        arr = gson.fromJson(br, type);

        allAccounts.addAll(arr);
    }

    public static void initialize(){
        readArrayOfAccountFromFile("Managers");
        readArrayOfAccountFromFile("Customers");
        readArrayOfAccountFromFile("Sellers");
        readArrayOfProductFromFile("Products");
        for (Account account : allAccounts){
            if (account instanceof Manager && account.getUsername().equals("Manager"))
                return;
        }
        try {
            allAccounts.add(new Manager("Admin","Admin","Admin","Admin@gmail.com",
                    "000","Admin" , null,0));
        }catch (Exception e){

        }
    }
}
