package Controller;


import Model.Account;

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

    public void createNewAccount()
    {

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
    class notFoundUserOrPass extends Exception
    {
        notFoundUserOrPass(String message)
        {
            super(message + "\ntry again");
        }
    }
}
