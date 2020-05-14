package Controller;

import Model.Account;
import Model.Manager;
import Model.Product;

public class ControlManager {

    Manager user = (Manager)(Control.getInstance().getUser());
    private static ControlManager instance;
    private ControlManager()
    {

    }
    public static ControlManager getInstance()
    {
        if(instance == null)
            instance = new ControlManager();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber)
    {
        new Manager(username,firstName,lastName,email,phoneNumber,password);
    }

    public void viewUsername(String username) throws Exception
    {
        if(!Account.getAllAccounts().keySet().contains(username))
        {
            throw new Exception("This username doesn't exist!");
        }
    }
    public void removeProduct(String id) throws Exception
    {
        if(!Product.getAllProducts().containsKey(id))
            throw new Exception("This product doesn't exist!");
        Product.getAllProducts().remove(id);
    }
}
