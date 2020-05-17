package Controller;


import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Company;

import java.nio.charset.Charset;
import java.util.*;

public class Control {

    Account user = null;
    private static Control instance;
    private Control()
    {
    }

    public String randomString(int n)
    {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String  AlphaNumericString
                = randomString
                .replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {

            if (Character.isLetter(AlphaNumericString.charAt(k))
                    && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k))
                    && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }
        // return the resultant string
        return r.toString();
    }
    public static Control getInstance() {
        if(instance == null)
            instance = new Control();
        return instance;
    }

    public Account getUser() {
        if(user == null)
            return null;
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
        setUser(Account.getAllAccounts().get(userName));
    }

    public void createAccount (String type, String username, String password, String firstName, String lastName, String email, String phoneNumber, String verifyPassword, Company company, boolean login) throws Exception
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
            ControlSeller.getInstance().createAccount(username,password,firstName,lastName,email,phoneNumber, company);
        }
        else if(type.equalsIgnoreCase("customer"))
        {
            ControlCustomer.getInstance().createAccount(username,password,firstName,lastName,email,phoneNumber);
        }
        if(login)
            login(username,password);
    }
    public boolean checkIfCustomer()
    {
        if(Control.getInstance().getUser() != null && Control.getInstance().getUser() instanceof Customer)
        {
            return true;
        }
        return false;
    }
    public void deleteUser(String username) throws Exception
    {
        if(!Account.getAllAccounts().keySet().contains(username))
        {
            throw new Exception("This username doesn't exist!");
        }
        if(Account.getAllAccounts().get(username).equals(user))
        {
            throw new Exception("you can't delete yourself!");
        }
        if(Account.getAllAccounts().get(username) instanceof Manager)
        {
            Manager.removeManager((Manager) Account.getAllAccounts().get(username));
        }
        else if(Account.getAllAccounts().get(username) instanceof Seller)
        {
            Seller.removeSeller((Seller) Account.getAllAccounts().get(username));
        }
        else if(Account.getAllAccounts().get(username) instanceof Customer)
        {
            Customer.removeCustomer((Customer) Account.getAllAccounts().get(username));
        }
        Account.getAllAccounts().remove(username);
    }
    public boolean timeCorrectMatch(String time)
    {
        if(time.matches("\\d{4}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}\\s+\\d{1,2}")) {
            return true;
        }
        return false;
    }
    public boolean doubleCheck(String string)
    {
        if(string.matches("\\d+.?\\d*"))
            return true;
        return false;
    }

    public void sortHashmap(HashMap map)
    {
        TreeMap<String, Integer> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        map.putAll(sorted);
    }

    public void sortHashmapByFeature(HashMap map)
    {

    }

    public void sortArraylist(ArrayList list)
    {

    }
    class notFoundUserOrPass extends Exception
    {
        notFoundUserOrPass(String message)
        {
            super(message + "\ntry again");
        }
    }



    public Map<String, Account> sortAllAccounts() {
        Map<String, Account> allAccounts = Account.getAllAccounts();
        /* Sort statement */
        allAccounts = sortByKey(allAccounts);
        return allAccounts;
    }
    
    public Map<String, Account> sortByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Account> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

       return sorted;
    }
}
