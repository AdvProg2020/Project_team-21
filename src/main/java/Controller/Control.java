package Controller;

import Model.Manager;
import Model.ShoppingCart;
import View.ErrorsAndExceptions;
import Model.Account;
import Model.*;

import javax.swing.text.View;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Control {
    Account currentAccount;
    ShoppingCart shoppingCart = new ShoppingCart();

    private static Control instance;
//    private Control()
//    {
//    }

    public void usernameTypeCheck(String username, String type) throws ErrorsAndExceptions.ExistedUsernameException,
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
        this.currentAccount = account;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void emptyShoppingCart (){
        shoppingCart= new ShoppingCart();
    }

    public static Matcher matcher ( String regex , String string ){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string);
    }

    protected String[] getPersonalInfo(Account account) {
        String[] info;
        if (account.Type().equals("customer")) {
            info = new String[7];
            info[6] = String.valueOf(((Customer) account).getBalance());
        } else if (account.Type().equals("seller")) {
            info = new String[8];
            info[6] = String.valueOf(((Seller) account).getBalance());
            info[7] = ((Seller) account).getCompanyName();
        } else
            info = new String[6];
        info[0] = account.getUsername();
        info[1] = account.Type();
        info[2] = account.getFirstName();
        info[3] = account.getLastName();
        info[4] = account.getEmail();
        info[5] = account.getPhoneNumber();
        return info;
    }

    public static Control getInstance() {
        return instance;
    }

    public Account getAccount() {
        return currentAccount;
    }

    public void setAccount(Account account) {
        this.currentAccount = account;
    }
}
