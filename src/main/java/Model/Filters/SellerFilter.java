package Model.Filters;

import Model.Account.Account;
import Model.Account.Seller;
import Model.Product;

import java.util.ArrayList;

public class SellerFilter extends Filter {
    private final String name;
    private String sellerUserName;

    public SellerFilter(String sellerUserName, ArrayList<Product> products) {
        this.name = "Product Name";
        this.sellerUserName = sellerUserName;
    }

    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products) {
            if (product.getSellers().contains(Seller.getSellerWithUsername(sellerUserName)))
                filteredProducts.add(product);
        }
    }

    public void removeDiffs(ArrayList<Product> filterdProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products) {
            if (!product.getSellers().contains(Seller.getSellerWithUsername(sellerUserName)))
                filterdProducts.remove(product);
        }
    }

    protected String show() {
        return name + " : " + sellerUserName;
    }

    public String getName() {
        return name;
    }
}
