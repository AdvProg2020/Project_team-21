package Client.Model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Product> productsList;

    public Category(String name) {
        this.name = name;
        productsList = new ArrayList<>();
    }
    public void addProduct(Product product){
        productsList.add(product);
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public String getName() {
        return name;
    }
}
