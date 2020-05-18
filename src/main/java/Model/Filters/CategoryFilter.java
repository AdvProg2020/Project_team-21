package Model.Filters;

import Model.Product;

import java.util.ArrayList;

public class CategoryFilter extends Filter {
    private final String name;
    private String categoryName;

    public CategoryFilter(String categoryName, ArrayList<Product> products) {
        this.name = "Category";
        this.categoryName = categoryName;
    }

    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getCategory().getName().equals(categoryName))
                filteredProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getCategory().getName().equals(categoryName))
                filteredProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + categoryName;
    }

    public String getName() {
        return name;
    }
}