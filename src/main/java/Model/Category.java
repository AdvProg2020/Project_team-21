package Model;

import java.util.ArrayList;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<Product> productsList;
    private ArrayList<Category> subCategories;


    // Initialization Block
    {
        productsList = new ArrayList<>();
        subCategories = new ArrayList<>();
    }

    public Category(String name){

        setName(name);
        allCategories.add(this);
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
    }

    public void removeProductFromCategory(Product product){
        productsList.remove(product);
    }

    public void addSubCategory(Category category){
        subCategories.add(category);
    }

    public void removeSubCategory(Category category){
        subCategories.remove(category);
    }

    public void removeCategory(Category category){
        allCategories.remove(category);
    }

    private void setName(String name){
        this.name = name;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }
}

