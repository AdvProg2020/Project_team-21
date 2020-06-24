package Model;

import Model.Account.Account;
import Model.Account.Seller;
import Model.Log.SellLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {

    private HashMap<Product, Integer> productsQuantity = new HashMap<>();
    private HashMap<Product, Seller> productSeller = new HashMap<>();
    private static ArrayList<ShoppingCart> allShoppingCarts = new ArrayList<>();

    private Account customer;
    private double price;

    public ShoppingCart(Account customer){
        setCustomer(customer);
        allShoppingCarts.add(this);
//        SaveData.saveData(this, customer.getUsername()+"ShoppingCart", SaveData.shoppingCartFile);
    }

//    public static void rewriteFiles(){
//        for (ShoppingCart cart : allShoppingCarts) {
//            File file = new File(cart.getCustomer().getUsername()+"ShoppingCart"+".txt");
//            file.delete();
//            SaveData.saveData(cart, cart.getCustomer().getUsername()+"ShoppingCart", SaveData.shoppingCartFile);
//        }
//    }

    public void setCustomer(Account customer){
        this.customer = customer;
    }

    public double getPrice() {
        return price;
    }

    public HashMap<Product, Integer> getProductsQuantity() {
        return productsQuantity;
    }

    public HashMap<Product, Seller> getProductSeller() {
        return productSeller;
    }

    public Account getCustomer(){
        return customer;
    }

    public void addProduct(Product product, int num, Seller seller)
    {
        productsQuantity.put(product, num);
        price += product.getPrice()*num;
        productSeller.put(product,seller);
    }

    public void increaseQuantity(Product product)
    {
        productsQuantity.put(product, productsQuantity.get(product)+1);
        price += product.getPrice();
    }
    public void decreaseQuantity(Product product)
    {
        if(productsQuantity.get(product)<=1)
        {
            removeProduct(product);
        }
        else
        {
            productsQuantity.put(product, productsQuantity.get(product)-1);
            price -= product.getPrice();
        }
    }
    public void removeProduct(Product product){
        productsQuantity.remove(product);
        price -= product.getPrice();
        productSeller.remove(product);
    }
    public void clearShoppingCart()
    {
        price = 0;
        productsQuantity.clear();
        productSeller.clear();
    }
//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.shoppingCartFile)));
//        for (Object object : objects) {
//            allShoppingCarts.add((ShoppingCart)(object));
//        }
//    }
}
