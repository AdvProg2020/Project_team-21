package Model.Filters;

import Model.Product;

import java.util.ArrayList;

public class CompanyNameFilter extends Filter {

    private final String name;
    private String companyName;

    public CompanyNameFilter(String companyName, ArrayList<Product> products) {
        this.name = "Company Name";
        this.companyName = companyName;
    }

    public String show() {
        return name + " : " + companyName;
    }

    public String getName() {
        return name;
    }

    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getCompany().getName().equals(companyName))
                filteredProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) {
        for (Product product : products) {
            if (!product.getCompany().getName().equals(companyName))
                filteredProducts.remove(product);
        }
    }


}
