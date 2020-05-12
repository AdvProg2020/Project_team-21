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
        if(checkIfCustomer())
        {
            ConsoleView.getInstance().errorInput("");
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
