package Controller;

import Model.Account.Customer;
import Model.Product;

public class ControlCustomer {
    private static ControlCustomer instance;
    private ControlCustomer()
    {

    }
    public static ControlCustomer getInstance()
    {
        if(instance == null)
            instance = new ControlCustomer();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber)
    {
        new Customer(username,firstName,lastName,email,phoneNumber,password);
    }
    private boolean checkProductExistance(String productID)
    {
        if(Product.getAllProducts().containsKey(productID))
            return true;
        return false;
    }
    public void increaseOrDecreaseProductInCart(String productID,Customer customer,boolean increase) throws Exception
    {
        if(!checkProductExistance(productID))
        {
            throw new Exception("This product doesn't exist!");
        }
        Product product = Product.getAllProducts().get(productID);
        if(!customer.getShoppingCart().getProducts().containsKey(product))
        {
            throw new Exception("You don't have this product");
        }
        if(increase)
        {
            customer.getShoppingCart().increaseQuantity(product);
        }
        else
        {
            customer.getShoppingCart().decreaseQuantity(product);
        }
    }

}
