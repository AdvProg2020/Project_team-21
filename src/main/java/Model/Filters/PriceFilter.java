package Model.Filters;

import Model.Product;

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

    public void apply(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getPrice() >= startRange && product.getPrice() <= endRange)
                filterdProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getPrice() <= startRange || product.getPrice() >= endRange)
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + startRange + " - " + endRange;
    }

    public String getName() {
        return name;
    }
}
