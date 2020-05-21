package View;

import Controller.Control;
import Model.Account.Account;
import Model.Company;

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

        Company company = null;
        boolean login = true;
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
            login = false;
            System.out.println("Enter your Company name");
            String companyName = scanner.nextLine();
            if(Company.getAllCompanies().containsKey(companyName))
            {
                company = Company.getAllCompanies().get(companyName);
            }
            else
            {
                System.out.println("Enter location of this company: ");
                String companyLocation = scanner.nextLine();
                company = new Company(companyName,companyLocation);
            }
        }
        try {
            Control.getInstance().createAccount(type,userName,password,firstName,lastName,email,phone,checkPassword,company,login);
            System.out.println("Wellcome "+firstName+"!");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLandingPageAfterSigninOrSignup());
            ConsoleView.getInstance().getLandingPageAfterSigninOrSignup().run();
        }catch (Exception e)
        {
            ConsoleView.getInstance().getLandingPageAfterSigninOrSignup().run();
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLandingPageAfterSigninOrSignup());
        }
    }


    @Override
    public void help()
    {
        System.out.println("Just enter what console wants from you.");
    }

    @Override
    public void sort() {

    }
}
