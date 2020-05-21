package Model;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
    private static Gson gson;

    public SaveData(){

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
        fileToClassMap.put(accountFile, Account.class);

        gson = new Gson();
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

        ArrayList<String> jsonFiles = new ArrayList<>();
        try {
//            FileReader reader = new FileReader(fileName);
//            while (reader.read()!=-1) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String character;
                StringBuilder name = new StringBuilder();

//                while ((character = reader.rea) != -1) {
            while ((character = reader.readLine()) != null) {
//                    if(character=='\n'){
                        jsonFiles.add(character);
                        character = "";
//                    } else {
//                        name.append(character);
//                    }
                }

                reader.close();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Object> objects = new ArrayList<>();
        for (String jsonFile : jsonFiles) {
            try {
//                BufferedReader reader = new BufferedReader(new FileReader(fileName));
//                StringBuilder stringBuilder = new StringBuilder();
//                char[] buffer = new char[10];
//                while (reader.read(buffer) != -1) {
//                    stringBuilder.append(new String(buffer));
//                    buffer = new char[10];
//                }
//                FileReader reader = new FileReader(String.valueOf(jsonFile));
//                int character;
//                StringBuilder jsonString = new StringBuilder();
//
//                while ((character = reader.read())!=-1) {
//                    jsonString.append(character);
//                }

                BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
                String character;
                String name = "";

//                while ((character = reader.rea) != -1) {
                while ((character = reader.readLine()) != null) {
//                    if(character=='\n'){
                    name = name.concat(character);
                    character = "";
//                    } else {
//                        name.append(character);
//                    }
                }


                Object object = readData(name, fileToClassMap.get(fileName));//, Account.class);//fileToClassMap.get(fileName));  // Object object =
//                objects.add(gson.fromJson(name, Account.class));
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
