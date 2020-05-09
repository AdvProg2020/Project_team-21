package View;

import Controller.Control;
import Model.Customer;

import java.util.Scanner;

public class UserSignupUI extends UI {
    private static UserSignupUI instance;
    private String type;
    private String userName;
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
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Re-Enter your password: ");
        String checkPassword = scanner.nextLine();
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your phone number: ");
        String phone = scanner.nextLine();

        //now do the control method

    }
    public boolean checkIfCustomer()
    {
        if(Control.getInstance().getUser() != null && !(Control.getInstance().getUser() instanceof Customer))
        {
            return true;
        }
        return false;
    }
}
