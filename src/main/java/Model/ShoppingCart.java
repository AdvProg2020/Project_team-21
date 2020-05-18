package Model;

import java.util.HashMap;

public class ShoppingCart {

    private HashMap<Product, Integer> products;
    private Account customer;

    public ShoppingCart(Account customer){
        setCustomer(customer);
    }

    public ShoppingCart() {
    }

    private void setCustomer(Account customer){
        this.customer = customer;
    }

    public Account getCustomer(){
        return customer;
    }

    public void addProduct(Product product, int num){
        products.put(product, num);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }
}
