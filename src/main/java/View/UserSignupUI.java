package View;

import Controller.Control;
import Model.Account;
import Model.Customer;
import Model.Manager;
import com.sun.tools.javac.Main;

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
        String companyName = "";
        //three exceptions
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Re-Enter your password: ");
        String checkPassword = scanner.nextLine();
        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your phone number: ");
        String phone = scanner.nextLine();
        if(type.equalsIgnoreCase("seller"))
        {
            System.out.println("Enter your Company name");
            companyName = scanner.nextLine();
        }
        try {
            Control.getInstance().createAccount(type,userName,password,firstName,lastName,email,phone,checkPassword,companyName);
        }catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLandingPageAfterSigninOrSignup());
        }
        System.out.println("Wellcome "+firstName+"!");
        ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLandingPageAfterSigninOrSignup());
        ConsoleView.getInstance().getLandingPageAfterSigninOrSignup().run();
    }


    @Override
    public void help()
    {
        System.out.println("Just enter what console wants from you.");
    }
}
