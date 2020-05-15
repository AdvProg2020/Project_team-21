package Model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    private static ArrayList<String> allFilesAdd = new ArrayList<>();
    private static Gson gson = new Gson();

//    {
//        allFilesAdd.add("Account.txt");
//        allFilesAdd.add("BuyLog.txt");
//        allFilesAdd.add("Category.txt");
//        allFilesAdd.add("Customer.txt");
//        allFilesAdd.add("DiscountCode.txt");
//        allFilesAdd.add("Manager.txt");
//        allFilesAdd.add("Off.txt");
//        allFilesAdd.add("Product.txt");
//        allFilesAdd.add("Review.txt");
//        allFilesAdd.add("Score.txt");
//        allFilesAdd.add("Seller.txt");
//        allFilesAdd.add("SellLog.txt");
//        allFilesAdd.add("ShoppingCart.txt");
//    }

    public void findFileNames(String fileAdds){
        try {
            FileReader reader = new FileReader(fileAdds);
            int character;
            StringBuilder fileName = new StringBuilder();

            while ((character = reader.read())!='\n') {
                fileName.append(character);
            }
            allFilesAdd.add(fileName.toString());
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadData(String fileAdds){
        for (String file : allFilesAdd) {
            try {
                FileReader reader = new FileReader(fileAdds);
                int character;
                StringBuilder jsonString = new StringBuilder();

                while ((character = reader.read())!=-1) {
                    jsonString.append(character);
                }
//                readData(jsonString.toString(), fileAdds);
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readData(String jsonString, Class className){

        gson.fromJson(jsonString, className);
    }


    public void writeData(Object object, String name, String fileAdds){

        String json = gson.toJson(object);

        try {
            name += ".json";
            FileWriter writer = new FileWriter(name, false);
            writer.write(json);

            FileWriter writer2 = new FileWriter(fileAdds, true);
            writer2.write((name+'\n'));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
