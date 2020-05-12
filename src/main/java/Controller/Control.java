package Controller;

import Model.Manager;
import Model.ShoppingCart;
import View.ErrorsAndExceptions;
import Model.Account;

import javax.swing.text.View;

public class Control {
    public static Account account;
    public static ShoppingCart shoppingCart;

    private static Control instance;
    private Control()
    {
    }

    public void usernameTypeValidation(String username, String type) throws ErrorsAndExceptions.ExistedUsernameException,
            ErrorsAndExceptions.AdminRegisterException {
        if (Account.getAccountByUsername(username) == null)
            throw new ErrorsAndExceptions.ExistedUsernameException(username);
        else if (type.equalsIgnoreCase("admin") && (Manager.getManager() == null))
            throw new ErrorsAndExceptions.AdminRegisterException();
    }

    public void login(String username, String password) throws ErrorsAndExceptions.WrongPasswordException, ErrorsAndExceptions.InvalidUsernameException {
        Account account = Account.getAccountByUsername(username);
        if (account == null)
            throw new ErrorsAndExceptions.InvalidUsernameException(username);
        if (!account.getPassword().equals(password))
            throw new ErrorsAndExceptions.WrongPasswordException();
        this.account = account;
    }



    public static Control getInstance() {
        return instance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
