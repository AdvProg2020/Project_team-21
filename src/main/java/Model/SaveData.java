package Model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveData {

    private static final String accountFile = "Account.txt";
    private static final String buyLogFile = "BuyLog.txt";
    private static final String categoryFile = "Category.txt";
    private static final String customerFile = "Customer.txt";
    private static final String discountCodeFile = "DiscountCode.txt";
    private static final String managerFile = "Manager.txt";
    private static final String offFile = "Off.txt";
    private static final String productFile = "Product.txt";
    private static final String reviewFile = "Review.txt";
    private static final String scoreFile = "Score.txt";
    private static final String sellerFile = "Seller.txt";
    private static final String sellLogFile = "SellLog.txt";
    private static final String shoppingCartFile = "ShoppingCart.txt";

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

    public void retrieveData(){
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

    public void reloadObject(String fileName){
            try {
                FileReader reader = new FileReader(fileName);
                int character;
                StringBuilder jsonString = new StringBuilder();

                while ((character = reader.read())!=-1) {
                    jsonString.append(character);
                }
                readData(jsonString.toString(), fileToClassMap.get(fileName));
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void readData(String jsonString, Class className){

        gson.fromJson(jsonString, className);
    }


    public void saveData(Object object, String name, String typeClass){

        typeClass += ".txt";
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
