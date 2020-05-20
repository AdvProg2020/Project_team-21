package Model.Filters;

import Model.Product;

import java.util.ArrayList;

public class FeaturesFilter extends Filter{
    private final String name;
    private String featureTitle;
    private String desiredFilter;

    public FeaturesFilter(String featureTitle, String desiredFilter, ArrayList<Product> products) {
        this.name = featureTitle;
        this.featureTitle = featureTitle;
        this.desiredFilter = desiredFilter;
    }

    @Override
    public String show() {
        return featureTitle + " : " + desiredFilter;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products){
            if (product.getSpecialFeatures().get(featureTitle).equals(desiredFilter))
                filteredProducts.add(product);
        }
    }

    @Override
    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products){
            if (!product.getSpecialFeatures().get(featureTitle).equals(desiredFilter))
                filteredProducts.remove(product);
        }
    }
}
