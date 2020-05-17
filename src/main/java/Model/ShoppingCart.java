package Model;

import Model.Account.Account;

import java.util.HashMap;

public class ShoppingCart {

    private HashMap<Product, Integer> products;
    private Account customer;
    private double price;

    public ShoppingCart(Account customer){
        setCustomer(customer);
    }


    private void setCustomer(Account customer){
        this.customer = customer;
    }

    public double getPrice() {
        return price;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public Account getCustomer(){
        return customer;
    }

    public void addProduct(Product product, int num)
    {
        products.put(product, num);
        price += product.getPrice()*num;
    }

    public void removeProduct(Product product,int num){
        products.remove(product);
        price -= product.getPrice()*num;
    }
}
