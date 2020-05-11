package Controller;

import Model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataBase <Public> {
    private static DataBase dataBase;
    private DataBase(){}

    public void saveAccount (Account account) throws IOException {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String Username=account.getUsername();
        String filePath= "Account Data: "+ File.separator;
        String fileName= Username + "'s json";
        File file = new File (filePath + File.separator+fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(gson.toJson(account));
        fileWriter.close();
    }

    public  void deleteAccount(Account account){
        String Username=account.getUsername();
        String filePath="Account Data: "+File.separator;
        String fileName=Username+"'s json";
        File file=new File(filePath+File.separator+fileName);
        file.delete();
    }

    public void saveProduct (Product product) throws IOException {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
//        String Id=product.getId();    //product remains
        String filePath= "Product Data: "+ File.separator;
//        String fileName= Id + "'s json";
//        File file = new File (filePath + File.separator+fileName);
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write(gson.toJson(product));
//        fileWriter.close();
    }

    public  void deleteProduct(Product product){
//        String Id=product.getId();
        String filePath="Product Data: "+File.separator;
//        String fileName=Id+"'s json";
//        File file=new File(filePath+File.separator+fileName);
//        file.delete();
    }

    public void saveDiscountCode (DiscountCode discountCode) throws IOException {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
//        String Id=discountCode.getUsername();
        String filePath= "DiscountCode Data: "+ File.separator;
//        String fileName= Id + "'s json";
//        File file = new File (filePath + File.separator+fileName);
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write(gson.toJson(discountCode));
//        fileWriter.close();
    }

    public  void deleteDiscountCode(DiscountCode discountCode){
//        String Id=product.getId();
        String filePath="DiscountCode Data: "+File.separator;
//        String fileName=Id+"'s json";
//        File file=new File(filePath+File.separator+fileName);
//        file.delete();
    }

    public void saveCategory (Category category) throws IOException {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String filePath= "Account Data: "+ File.separator;
        String fileName= category.getName() + "'s json";
        File file = new File (filePath + File.separator+fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(gson.toJson(category));
        fileWriter.close();
    }

    public  void deleteCategory(Category category){
        String filePath="Category Data: "+File.separator;
//        String fileName=category.getName() + "'s json";
//        File file=new File(filePath+File.separator+fileName);
//        file.delete();
    }

    public ArrayList<String> printFolderContent(String folderName){
        ArrayList<String> fileNames=new ArrayList();
        String path="Resource"+File.separator+folderName;
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                fileNames.add(file.getName().replace(".json",""));
            }
        }
        return fileNames;
    }

}
