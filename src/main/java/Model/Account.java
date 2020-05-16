package Model;

import View.ErrorsAndExceptions;

import java.util.ArrayList;

public abstract class Account {
    private static ArrayList<Account> allAccounts =new ArrayList<Account>();
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private ArrayList<DiscountCode> discountList = new ArrayList<DiscountCode>();
    private double credit;

    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password, ArrayList<DiscountCode> discountList, double credit) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.discountList = discountList;
        this.credit = credit;
        allAccounts.add(this);
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

    public void setFirstName(String firstName) throws ErrorsAndExceptions.SetFirstNameError {
        if(!firstName.matches("[A-Za-z]+"))
            throw new ErrorsAndExceptions.SetFirstNameError(firstName);
        else
        this.firstName = firstName;
    }

    public void setLastName(String lastName) throws ErrorsAndExceptions.SetLastNameError {
        if(!firstName.matches("[A-Za-z]+"))
            throw new ErrorsAndExceptions.SetLastNameError(lastName);
        else
            this.lastName = lastName;
    }

    public void setEmail(String email) throws ErrorsAndExceptions.SetEmailError{
        if (!email.matches("\\S+@\\S+\\.\\S+")){
            throw new ErrorsAndExceptions.SetEmailError("Invalid Email");
        }
        else
            this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) throws ErrorsAndExceptions.SetPhoneError{
        if (!phoneNumber.matches("\\d+"))
            throw new ErrorsAndExceptions.SetPhoneError("Invalid PhoneNumber");
        else
            this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) throws ErrorsAndExceptions.SetPasswordError{
        if (password.length() < 5 || !password.matches("\\S+")){
            throw new ErrorsAndExceptions.SetPasswordError("Weak or Invalid Password");
        }
        else
            this.password = password;
    }

    public void setUsername(String username) throws ErrorsAndExceptions.SetUserNameError {
        if (!username.matches("[A-Za-z_0-9]+")){
            throw new ErrorsAndExceptions.SetUserNameError("Invalid Username");
        }
        else
            this.username = username;
    }

    public void removeAccount (Account account){
        allAccounts.remove(account);
    }

    public static Account getAccountByUsername(String username) {
        for (Account account : allAccounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", discountList=" + discountList +
                ", credit=" + credit +
                '}';
    }

    public abstract String Type();
}
