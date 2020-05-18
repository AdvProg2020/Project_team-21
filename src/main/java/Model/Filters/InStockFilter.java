package Model.Filters;

import Model.Product;

import java.util.ArrayList;

public class InStockFilter extends Filter {

    private final String name;

    public InStockFilter(ArrayList<Product> products) {
        this.name = "Only Available Products";
    }

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.doesExist())
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.doesExist())
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name;
    }

    public String getName() {
        return name;
    }
}
