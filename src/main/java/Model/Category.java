package Model;

import java.io.File;
import java.util.ArrayList;

public class Category implements Comparable<Category>{
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<Product> productsList;
    private ArrayList<Category> subCategories;
    private ArrayList<String> specialFeatures = new ArrayList<String>();

    // Initialization Block
    {
        productsList = new ArrayList<>();
        subCategories = new ArrayList<>();
    }

    public Category(String name, ArrayList<Product> products)
    {
        for (Product product : products) {
            addProductToCategory(product);
        }
        setName(name);
        allCategories.add(this);
        SaveData.saveData(this, getName(), SaveData.categoryFile);
    }

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
        productsList.add(product);
        product.setCategory(this);
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public void removeProductFromCategory(Product product){
        product.setCategory(null);
        productsList.remove(product);
    }

    public void addSubCategory(Category category){
        subCategories.add(category);
    }

    public void removeSubCategory(Category category){
        subCategories.remove(category);
    }

    public static void removeCategory(Category category){
        for (Product product : category.getProductsList())
        {
            product.setCategory(null);
        }
        allCategories.remove(category);

        File file = new File(category.getName()+".txt");
        if(file.delete()){
//            System.out.println("yes");
        } else {
//            System.out.println("hah");
        }
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
        return subCategories;
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.categoryFile)));
        for (Object object : objects) {
            allCategories.add((Category) (object));
        }
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
        for (Product product : productsList){
            product.addSpecialFeature(specialFeature,null);
        }
    }

    public void removeSpecialFeature(String specialFeature){
        specialFeatures.remove(specialFeature);
        removeSpecialFeatureFromProducts(specialFeature);
    }

    public void removeSpecialFeatureFromProducts(String specialFeature){
        for (Product product : productsList){
            product.removeSpecialFeature(specialFeature);
        }
    }

    public boolean isThereSpecialFeature(String specialFeature){
        return specialFeatures.contains(specialFeature);
    }



}

