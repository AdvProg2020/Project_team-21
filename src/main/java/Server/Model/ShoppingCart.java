package Server.Model;

import Server.DatabaseHandler;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {

    private static ArrayList<ShoppingCart> allShoppingCarts = new ArrayList<>();
    private HashMap<String, Integer> productsQuantity = new HashMap<>();
    private HashMap<String,String> productSeller = new HashMap<>();
    private String customer;
    private String cartID;
    private double price;

    public ShoppingCart(Account customer, String id){
        if(customer != null)
            setCustomer(customer);
        allShoppingCarts.add(this);
        cartID = id;
//        SaveData.saveData(this, customer.getUsername()+"ShoppingCart", SaveData.shoppingCartFile);
    }

//    public static void rewriteFiles(){
//        for (ShoppingCart shoppingCart : ShoppingCart.getAllShoppingCarts()) {
//            SaveData.saveDataRunning(shoppingCart, shoppingCart.getCustomer().getUsername() + "ShoppingCart", SaveData.shoppingCartFile);
//        }
//    }

    public void setCustomer(Account customer){
        this.customer = customer.getUsername();
    }

    public double getPrice() {
        return price;
    }

    public HashMap<Product, Integer> getProductsQuantity() {
        HashMap<Product,Integer> res = new HashMap<>();
        for (String s : productsQuantity.keySet()) {
            res.put(Product.getAllProducts().get(s),productsQuantity.get(s));
        }
        return res;
    }

    public HashMap<Product, Seller> getProductSeller() {
        HashMap<Product,Seller> res = new HashMap<>();
        for (String s : productSeller.keySet()) {
            res.put(Product.getAllProducts().get(s),(Seller)Account.getAllAccounts().get(productSeller.get(s)));
        }
        return res;
    }

    public Account getCustomer(){
        return Account.getAllAccounts().get(customer);
    }

    public void addProduct(Product product, int num, Seller seller)
    {
        productsQuantity.put(product.getProductId(), num);
        price += product.getPrice()*num;
        productSeller.put(product.getProductId(),seller.getUsername());
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getCartID() {
        return cartID;
    }

    public void increaseQuantity(Product product)
    {
        productsQuantity.put(product.getProductId(), productsQuantity.get(product.getProductId())+1);
        price += product.getPrice();
    }
    public void decreaseQuantity(Product product)
    {
        if(productsQuantity.get(product.getProductId())<=1)
        {
            removeProduct(product);
        }
        else
        {
            productsQuantity.put(product.getProductId(), productsQuantity.get(product.getProductId())-1);
            price -= product.getPrice();
        }
    }
    public void removeProduct(Product product){
        if(productsQuantity.containsKey(product.getProductId())){
            System.out.println("*");
            productsQuantity.remove(product.getProductId());
            price -= product.getPrice();
            productSeller.remove(product.getProductId());
        }
    }


    public void clearShoppingCart()
    {
        price = 0;
        productsQuantity.clear();
        productSeller.clear();
    }
    public boolean containsProduct(Product product){
        if(productsQuantity.containsKey(product.getProductId()))
            return true;
        return false;
    }

    public static ArrayList<ShoppingCart> getAllShoppingCarts() {
        return allShoppingCarts;
    }

//    public static void getObjectFromDatabase(){
//        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.shoppingCartFile)));
//        for (Object object : objects) {
//            allShoppingCarts.add((ShoppingCart)(object));
//        }
//    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<ShoppingCart> shoppingCarts  = new ArrayList<>(DatabaseHandler.selectFromShoppingCart());
        for (ShoppingCart shoppingCart : shoppingCarts) {
            allShoppingCarts.add(shoppingCart);
        }
    }
}
