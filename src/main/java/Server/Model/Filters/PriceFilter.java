package Server.Model.Filters;

import Server.Model.Product;

import java.util.ArrayList;

public class PriceFilter extends Filter {

    private final String name;
    private double startRange;
    private double endRange;

    public PriceFilter(double startRange, double endRange, ArrayList<Product> products) {
        this.name = "Price";
        this.startRange = startRange;
        this.endRange = endRange;
    }

    @Override
    public String show() {
        return name + " : " + startRange + " - " + endRange;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getPrice() >= startRange && product.getPrice() <= endRange)
                filteredProducts.add(product);
        }
    }

    @Override
    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getPrice() <= startRange || product.getPrice() >= endRange)
                filteredProducts.remove(product);
        }
    }
}
