package View;

import Controller.Control;

import java.lang.constant.Constable;

public class UserLoginUI extends UI {
    private static UserLoginUI instance;

    private UserLoginUI()
    {

    }
    public static UserLoginUI getInstance() {
        if(instance == null)
            instance = new UserLoginUI();
        return instance;
    }

    /*
     else if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(.+)"))
        {
            if(user != null && !(user instanceof Customer))
            {
                errorInput("you can't make a new user");
            }
            else
            {
                goToNextPage(UserSignupUI.getInstance());
            }
        }
     */
    @Override
    public void run()
    {
        System.out.println("login or SignUp!");
    }
    public void login(String userName)
    {
        System.out.println("enter your password: ");
        try {
            Control.getInstance().login(userName,ConsoleView.getScanner().nextLine());
            System.out.println("Welcome " + Control.getInstance().getUser().getFirstName());
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLandingPageAfterSigninOrSignup());
            ConsoleView.getInstance().getLandingPageAfterSigninOrSignup().run();
        }catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ConsoleView.getInstance().getLandingPageAfterSigninOrSignup());
        }
    }

    @Override
    public void help() {
        System.out.println("Login: login [Username]\nSignup: create account [type] [username]");
    }

    @Override
    public void sort() {

    }
}
