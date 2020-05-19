package Model.Filters;

import Controller.Control;
import Model.Product;

import java.util.ArrayList;

public abstract class Filter {
    public static String showCurrentFilters() {
        String current = "";
        for (Filter filter : Control.currentFilters) {
            current = current.concat(filter.show());
            current = current.concat("\n");
        }
        return current;
    }

    public abstract String show();

    public abstract String getName();

    public abstract void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception;

    public abstract void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception;

    public static ArrayList<Product> applyFilter(ArrayList<Product> products) throws Exception {
        ArrayList<Product> filteredProducts = new ArrayList<Product>();
        for (Filter currentFilter : Control.currentFilters) {
            currentFilter.apply(filteredProducts, products);
        }
        for (Filter currentFilter : Control.currentFilters) {
            currentFilter.removeDiffs(filteredProducts, products);
        }
        if (filteredProducts.isEmpty())
            return products;
        return filteredProducts;
    }
}
