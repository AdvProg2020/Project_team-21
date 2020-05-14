package Controller;


import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;
import View.ConsoleView;

public class Control {

    Account user;
    private static Control instance;
    private Control()
    {
    }

    public static Control getInstance() {
        return instance;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public void showPopularProducts()
    {

    }
    public void showSales()
    {

    }
    public void login(String userName,String password) throws notFoundUserOrPass
    {
        if(!(Account.getAllAccounts().containsKey(userName)))
        {
            throw new notFoundUserOrPass("userName didn't found!");
        }
        else if(!(Account.getAllAccounts().get(userName).getPassword().equals(password)))
        {
            throw new notFoundUserOrPass("password didn't match!");
        }
        user = Account.getAllAccounts().get(userName);
    }

    public void createAccount (String type,String username, String password, String firstName, String lastName, String email, String phoneNumber,String verifyPassword,String companyName) throws Exception
    {
        //5 errors
        if(!verifyPassword.equals(password))
        {
            throw new Exception("Your password doesn't match");
        }
        if(checkIfCustomer())
        {
            throw new Exception("You are a customer and can't make a new user");
        }
        if(!(user instanceof Manager) && type.equalsIgnoreCase("manager") && !Manager.getAllManagers().isEmpty())
        {
            throw new Exception("You should be a manager to create a manager account");
        }
        if(!(type.matches("(?i)customer|manager|seller")))
        {
            throw new Exception("There is no type of account like that!");
        }
        if(Account.getAllAccounts().containsKey(username))
        {
            throw new Exception("This username already exists!");
        }

        if(type.equalsIgnoreCase("manager"))
        {
            ControlManager.getInstance().createAccount(username,password,firstName,lastName,email,phoneNumber);
        }
        else if(type.equalsIgnoreCase("seller"))
        {
            ControlSeller.getInstance().createAccount(username,password,firstName,lastName,email,phoneNumber,companyName);
        }
        else if(type.equalsIgnoreCase("customer"))
        {
            ControlCustomer.getInstance().createAccount(username,password,firstName,lastName,email,phoneNumber);
        }
    }
    public boolean checkIfCustomer()
    {
        if(Control.getInstance().getUser() != null && !(Control.getInstance().getUser() instanceof Customer))
        {
            return true;
        }
        return false;
    }

    class notFoundUserOrPass extends Exception
    {
        notFoundUserOrPass(String message)
        {
            super(message + "\ntry again");
        }
    }
}
