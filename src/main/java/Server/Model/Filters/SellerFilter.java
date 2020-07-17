package Server.Model.Filters;

import Server.Model.Account.Account;
import Server.Model.Account.Seller;
import Server.Model.Product;

import java.util.ArrayList;

public class SellerFilter extends Filter {
    private final String name;
    private String sellerUserName;

    public SellerFilter(String sellerUserName, ArrayList<Product> products) {
        this.name = "Product Name";
        this.sellerUserName = sellerUserName;
    }

    @Override
    public String show() {
        return name + " : " + sellerUserName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void apply(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products) {
            if (product.getSellers().contains(Seller.getSellerWithUsername(sellerUserName)))
                filteredProducts.add(product);
        }
    }

    @Override
    public void removeDiffs(ArrayList<Product> filteredProducts, ArrayList<Product> products) throws Exception {
        for (Product product : products) {
            if (!product.getSellers().contains(Seller.getSellerWithUsername(sellerUserName)))
                filteredProducts.remove(product);
        }
    }
}
