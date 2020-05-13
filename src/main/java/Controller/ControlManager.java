package Controller;

import java.util.ArrayList;
import Model.*;
import View.ErrorsAndExceptions;

public class ControlManager extends Control{

    public ArrayList<String> manageAccounts (){
        ArrayList<String> accounts =  new ArrayList<>();
        for(Account account : Account.getAllAccounts()){
            accounts.add(account.getUsername());
        }
        return accounts;
    }

    public String[] viewUsernameDetail (String username) throws ErrorsAndExceptions.InvalidUsernameException{
        Account account = Account.getAccountByUsername(username);
        if (account==null){
            throw new ErrorsAndExceptions.InvalidUsernameException(username);
        }
        else {
            return getPersonalInfo(account);
        }
    }


}
