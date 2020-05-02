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
}
