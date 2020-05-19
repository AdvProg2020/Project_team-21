package Model.Account;

import Model.DiscountCode;

import Controller.Sort;
import Model.SaveData;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Account {
    private static HashMap<String, Account> allAccounts = new HashMap<>();
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private double credit;

    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = 0;
        allAccounts.put(username,this);
        SaveData.saveData(this, (getUsername()+getPassword()), SaveData.accountFile);
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public static HashMap<String, Account> getAllAccounts() {
        return allAccounts;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void removeAccount (Account account){
        allAccounts.remove(account);
    }

    public abstract String getType();
    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                '}';
    }

    private static void setAllAccounts(HashMap<String, Account> allAccounts) {
        Account.allAccounts = allAccounts;
    }

    public void sortAllAccounts(){
//        Account.setAllAccounts((HashMap<String, Account>) Sort.sortAccountHashMap(getAllAccounts()));
    }

    public static void getObjectFromDatabase(){
        allAccounts.put(((Account)SaveData.reloadObject(SaveData.accountFile)).getUsername() ,(Account)SaveData.reloadObject(SaveData.accountFile));
    }

}
