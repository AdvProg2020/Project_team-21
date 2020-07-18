package Server.Model.Account;

import Server.Model.DiscountCode;

import Server.Controller.Sort;
import Server.Model.SaveData;

import java.io.File;
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
    private String address = "--";
    private String imagePath;
    private String bankAccountId;

    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = 0;
        imagePath = photo;
        bankAccountId = "";
        if(!(this instanceof Seller))
            allAccounts.put(username,this);
        SaveData.saveData(this, (getUsername()+getPassword()), SaveData.accountFile);
    }

//    public static void rewriteFiles(){
//        for (String s : Account.getAllAccounts().keySet()) {
//            Account account = Account.getAllAccounts().get(s);
//            File file = new File(s+account.getPassword()+".txt");
//            file.delete();
//            SaveData.saveData(account, (s+account.getPassword()), SaveData.accountFile);
//        }
//    }

    public static void rewriteFiles(){
        for (String s : Account.getAllAccounts().keySet()) {
            Account account = Account.getAllAccounts().get(s);
            SaveData.saveDataRunning(account, (s+account.getPassword()), SaveData.accountFile);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(address == null)
            address = "No Address";
        this.address = address;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        if(imagePath == null)
            imagePath = "profilePhotos/account_icon.png";
        return imagePath;
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

    public static void removeAccount (Account account){
        allAccounts.remove(account);
    }
    public static void addAccount (Account account){
        allAccounts.put(account.getUsername(),account);
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
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.accountFile)));
        for (Object object : objects) {
            allAccounts.put(((Account)object).getUsername() ,(Account)(object));
        }
    }
}
