package Model.Filters;

import Model.Product;

import java.util.ArrayList;

public class ProductNameFilter extends Filter {

    private final String name;
    private String productName;

    public ProductNameFilter(String productName, ArrayList<Product> products) {
        this.name = "Product Name";
        this.productName = productName;
    }

    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getName().equals(productName))
                filteredProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getName().equals(productName))
                filteredProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + productName;
    }

    public String getName() {
        return name;
    }


}
