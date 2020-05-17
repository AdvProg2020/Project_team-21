package Model.Filters;

import Model.Product;

import java.util.ArrayList;

public class BrandFilter extends Filter {

    private final String name;
    private String brandName;

    public BrandFilter(String brandName, ArrayList<Product> products) {
        this.name = "Brand";
        this.brandName = brandName;
    }

    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getCompany().getName().equals(brandName))
                filteredProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getCompany().getName().equals(brandName))
                filteredProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + brandName;
    }

    public String getName() {
        return name;
    }
}
