package Server.Model;

import Server.DatabaseHandler;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Category implements Comparable<Category>, Serializable {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private static HashMap<String,Category> initialAllCategories = new HashMap<>();
    private String name;
    private ArrayList<String> productsList;
    private ArrayList<String> subCategories;
    private static Category allProducts = new Category("AllProducts",new ArrayList<>());

    private ArrayList<String> specialFeatures = new ArrayList<String>();

    // Initialization Block
    {
        productsList = new ArrayList<>();
        subCategories = new ArrayList<>();
    }

    public Category(String name, ArrayList<Product> products)
    {
        if(products != null){
            for (Product product : products) {
                addProductToCategory(product);
            }
        }
        setName(name);
        if(!name.equals("AllProducts"))
            allCategories.add(this);
//        SaveData.saveData(this, getName()+"category", SaveData.categoryFile);
    }
//    public static void rewriteFiles(){
//        for (Category category : allCategories) {
//            SaveData.saveDataRunning(category, category.getName()+"category", SaveData.categoryFile);
//        }
//    }

    public String getName() {
        return name;
    }

    public Category findCategoryByName(String name){
        for (Category allCategory : allCategories) {
            if(allCategory.getName().equals(name)){
                return allCategory;
            }
        }
        return null;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void addProductToCategory(Product product){
        productsList.add(product.getProductId());
        product.setCategory(this);
    }

    public ArrayList<Product> getProductsList() {
        ArrayList<Product> res = new ArrayList<>();
        for (String s : productsList) {
            res.add(Product.getAllProducts().get(s));
        }
        return res;
    }

    public void removeProductFromCategory(Product product){
        product.setCategory(allProducts);
        productsList.remove(product.getProductId());
    }

    public void addSubCategory(Category category){
        subCategories.add(category.getName());
        allCategories.add(category);
    }

    public void removeSubCategory(Category category){
        subCategories.remove(category.getName());
        allCategories.remove(category);
    }

//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.categoryFile)));
//        for (Object object : objects) {
//            initialAllCategories.put(((Category)object).getName(),(Category)object);
//        }
//        for (String s : initialAllCategories.keySet()) {
//            allCategories.add(initialAllCategories.get(s));
//        }
//    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Category> categories  = new ArrayList<>(DatabaseHandler.selectFromCategory());
        for (Category category : categories) {

            initialAllCategories.put((category.getName()),category);
            allCategories.add(category);
        }
    }

    public static void removeCategory(Category category){
        for (Product product : category.getProductsList())
        {
            product.setCategory(allProducts);
        }
        allCategories.remove(category);
//        File file = new File("Database/" + category.getName() + "category" +".json");
//        file.delete();
    }

    public void setName(String name){
        this.name = name;
    }

//    public static ArrayList<Category> getAllCategories() {
//        return allCategories;
//    }

    @Override
    public int compareTo(Category o) {
        return getName().compareTo(o.getName());
    }

    public ArrayList<Category> getSubCategories() {
        ArrayList<Category> res = new ArrayList<>();
        for (String subCategory : subCategories) {
            for (Category category : Category.getAllCategories()) {
                if(subCategory.equals(category.getName())){
                    res.add(category);
                    break;
                }
            }
        }
        return res;
    }

    public String showSpecialFeatures(){
        StringBuilder features = new StringBuilder();
        features.append("specialFeatures");
        for (String specialFeature : specialFeatures){
            features.append("\n").append(specialFeature);
        }
        return features.toString();
    }

    public ArrayList<String> getSpecialFeatures() {
        return specialFeatures;
    }

    public void addSpecialFeature(String specialFeature){
        if (!specialFeatures.contains(specialFeature)){
            specialFeatures.add(specialFeature);
            addSpecialFeatureToProducts(specialFeature);
        }
    }

    public void addSpecialFeatureToProducts(String specialFeature){
        for (Product product : getProductsList()){
            product.addSpecialFeature(specialFeature,null);
        }
    }

    public void removeSpecialFeature(String specialFeature){
        specialFeatures.remove(specialFeature);
        removeSpecialFeatureFromProducts(specialFeature);
    }

    public void removeSpecialFeatureFromProducts(String specialFeature){
        for (Product product : getProductsList()){
            product.removeSpecialFeature(specialFeature);
        }
    }

    public boolean isThereSpecialFeature(String specialFeature){
        return specialFeatures.contains(specialFeature);
    }

}

