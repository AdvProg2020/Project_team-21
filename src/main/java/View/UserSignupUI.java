package View;

import Controller.Control;
import Model.Account;
import Model.Customer;
import Model.Manager;

import java.io.Console;
import java.util.Scanner;

public class UserSignupUI extends UI {
    private static UserSignupUI instance;
    private String type;
    private String userName;
    private Account user = Control.getInstance().getUser();
    public static UserSignupUI getInstance() {
        if(instance == null)
            instance = new UserSignupUI();
        return instance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void run()
    {
        //three exceptions
        if(checkIfCustomer())
        {
            ConsoleView.getInstance().errorInput("You are a customer and can't make a new user",ConsoleView.getInstance().getLastMenu());
        }
        if(!(user instanceof Manager) && type.equalsIgnoreCase("manager") && !Manager.getAllManagers().isEmpty())
        {
            ConsoleView.getInstance().errorInput("You should be a manager to create a manager account" , ConsoleView.getInstance().getLastMenu());
        }
        if(!(type.matches("(?i)customer|manager|seller")))
        {
            ConsoleView.getInstance().errorInput("There is no type of account like that!",ConsoleView.getInstance().getLastMenu());
        }
        if(Account.getAllAccounts().containsKey(userName))
        {
            ConsoleView.getInstance().errorInput("This username already exists!",ConsoleView.getInstance().getLastMenu());
        }
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Re-Enter your password: ");
        String checkPassword = scanner.nextLine();
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your phone number: ");
        String phone = scanner.nextLine();

        Control.getInstance().createAccount(type,userName,password,firstName,lastName,email,phone);

    }
    public boolean checkIfCustomer()
    {
        if(Control.getInstance().getUser() != null && !(Control.getInstance().getUser() instanceof Customer))
        {
            return true;
        }
        return false;
    }

    @Override
    public void help()
    {
        System.out.println("Just enter what console wants from you.");
    }
}
