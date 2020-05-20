package Model;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveData {

    public static final String accountFile = "Account.txt";
    public static final String buyLogFile = "BuyLog.txt";
    public static final String categoryFile = "Category.txt";
    public static final String customerFile = "Customer.txt";
    public static final String discountCodeFile = "DiscountCode.txt";
    public static final String managerFile = "Manager.txt";
    public static final String offFile = "Off.txt";
    public static final String productFile = "Product.txt";
    public static final String reviewFile = "Review.txt";
    public static final String scoreFile = "Score.txt";
    public static final String sellerFile = "Seller.txt";
    public static final String sellLogFile = "SellLog.txt";
    public static final String shoppingCartFile = "ShoppingCart.txt";

    private static HashMap<String, Class> fileToClassMap = new HashMap<>();
    private static Gson gson = new Gson();

    {
        fileToClassMap.put(accountFile, Account.class);
        fileToClassMap.put(buyLogFile, BuyLog.class);
        fileToClassMap.put(categoryFile, Category.class);
        fileToClassMap.put(customerFile, Customer.class);
        fileToClassMap.put(discountCodeFile, DiscountCode.class);
        fileToClassMap.put(managerFile, Manager.class);
        fileToClassMap.put(offFile, Off.class);
        fileToClassMap.put(productFile, Product.class);
        fileToClassMap.put(reviewFile, Review.class);
        fileToClassMap.put(scoreFile, Score.class);
        fileToClassMap.put(sellerFile, Seller.class);
        fileToClassMap.put(sellLogFile, SellLog.class);
        fileToClassMap.put(shoppingCartFile, ShoppingCart.class);
    }

    public static void retrieveData(){
        reloadObject(accountFile);
        reloadObject(buyLogFile);
        reloadObject(categoryFile);
        reloadObject(customerFile);
        reloadObject(discountCodeFile);
        reloadObject(managerFile);
        reloadObject(offFile);
        reloadObject(productFile);
        reloadObject(reviewFile);
        reloadObject(scoreFile);
        reloadObject(sellerFile);
        reloadObject(sellLogFile);
        reloadObject(shoppingCartFile);
    }

    public static ArrayList<Object> reloadObject(String fileName){

        ArrayList<StringBuilder> jsonFiles = new ArrayList<>();
        try {
            FileReader reader = new FileReader(fileName);
            while (reader.read()!=-1) {
                int character;
                StringBuilder name = new StringBuilder();

                while ((character = reader.read()) != '\n') {
                    name.append(character);
                }
                jsonFiles.add(name);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Object> objects = new ArrayList<>();
        for (StringBuilder jsonFile : jsonFiles) {
            try {
                FileReader reader = new FileReader(String.valueOf(jsonFile));
                int character;
                StringBuilder jsonString = new StringBuilder();

                while ((character = reader.read())!=-1) {
                    jsonString.append(character);
                }
                Object object = readData(jsonString.toString(), fileToClassMap.get(fileName));
                reader.close();
                objects.add(object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    private static Object readData(String jsonString, Class className){

        return gson.fromJson(jsonString, className);
    }

    public static void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
    }

    public static void createAllFiles(){

        try {
            createFile(accountFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(buyLogFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(categoryFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(customerFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(discountCodeFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(managerFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(offFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(productFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(reviewFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(scoreFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(sellerFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(sellLogFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }

        try {
            createFile(shoppingCartFile);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public static void saveData(Object object, String name, String typeClass) {



//        typeClass += ".txt";
        String json = gson.toJson(object);

        try {
            name += ".json";
            FileWriter writer = new FileWriter(name, false);
            writer.write(json);

            FileWriter writer2 = new FileWriter(typeClass, true);
            writer2.write((name+'\n'));

            writer.close();
            writer2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
