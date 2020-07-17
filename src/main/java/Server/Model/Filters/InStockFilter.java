package Server.Model.Filters;

import Server.Model.Product;

import java.util.ArrayList;

public class InStockFilter extends Filter {

    private final String name;

    public InStockFilter(ArrayList<Product> products) {
        this.name = "Only Available Products";
    }

    @Override
    public String show() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.doesExist())
                filteredProducts.add(product);
        }
    }

    @Override
    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.doesExist())
                filteredProducts.remove(product);
        }
    }
}
