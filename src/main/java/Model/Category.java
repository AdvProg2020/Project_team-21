package Model;

import java.util.ArrayList;

public class Category implements Comparable<Category>{
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<Product> productsList;
    private ArrayList<Category> subCategories;

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
    }

    public void setName(String name){
        this.name = name;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    @Override
    public int compareTo(Category o) {
        return getName().compareTo(o.getName());
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }

    public static void getObjectFromDatabase(){
        allCategories.add((Category) SaveData.reloadObject(SaveData.categoryFile));
    }

}

