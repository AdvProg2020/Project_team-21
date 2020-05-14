package View;

import Controller.Control;
import Model.Account;
import Model.Customer;
import Model.Manager;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView{
    private static Scanner scanner;
    private UI currentMenu;
    private ArrayList<UI> seenPages;
    private static ConsoleView instance;
    private Account user = Control.getInstance().getUser();
    private UI landingPageAfterSigninOrSignup;
    private ConsoleView()
    {
        scanner = new Scanner(System.in);
        seenPages = new ArrayList<>();
    }
    public static ConsoleView getInstance() {
        if(instance == null)
            instance = new ConsoleView();
        return instance;
    }

    public UI getLandingPageAfterSigninOrSignup() {
        return landingPageAfterSigninOrSignup;
    }

    public void setLandingPageAfterSigninOrSignup(UI landingPageAfterSigninOrSignup) {
        this.landingPageAfterSigninOrSignup = landingPageAfterSigninOrSignup;
    }

    public void setCurrentMenu(UI currentMenu) {
        this.currentMenu = currentMenu;
    }

    public UI getCurrentMenu() {
        return currentMenu;
    }
    public UI getLastMenu()
    {
        return seenPages.get(seenPages.size()-2);
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public void goToNextPage(UI menu)
    {
        seenPages.add(currentMenu);
        currentMenu = menu;
    }
    public void processInput(String input)
    {
        if(input.trim().matches("(?i)main\\s*menu"))
        {
            goToNextPage(MainMenuUI.getInstance());
        }
        else if(input.trim().matches("(?i)(login|signup|(view\\s*personal\\s*info))"))
        {
            UserInfoUI.getInstance().setNewToInfo(true);
            goToNextPage(UserInfoUI.getInstance());
        }
        else if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(\\S+)") && currentMenu.equals(UserLoginUI.getInstance()))
        {
                UserSignupUI.getInstance().setType(input.trim().split("\\s+")[2]);
                UserSignupUI.getInstance().setUserName(input.trim().split("\\s+")[3]);
                goToNextPage(UserSignupUI.getInstance());
        }
        else if(input.trim().matches("(?i)login\\s+(.+)") && currentMenu.equals(UserLoginUI.getInstance()))
        {
            UserLoginUI.getInstance().login(input.trim().split("\\s+")[1]);
            goToNextPage(UserInfoUI.getInstance());
        }
        if(currentMenu.equals(UserInfoUI.getInstance()))
        {
            if(input.trim().matches("(?i)show\\s+password") && currentMenu.equals(UserInfoUI.getInstance()))
            {
                UserInfoUI.getInstance().setShowPassword(true);
            }
            if(user instanceof Manager)
            {
                if(input.trim().matches("(?i)manage\\s+users") && user instanceof Manager && currentMenu.equals(UserInfoUI.getInstance()))
                {
                    UserInfoUI.getInstance().setManagerUsers(true);
                }
                else if(input.trim().matches("(?i)view\\s+(\\S+)"))
                {
                    UserInfoUI.getInstance().setViewUsername(true);
                    UserInfoUI.getInstance().setViewUsernameUser(input.split("\\s+")[1]);
                }
                else if(input.trim().matches("(?i)delete\\s+user\\s+(\\S+)"))
                {
                    UserInfoUI.getInstance().setDeleteUserUsername(input.split("\\s+")[2]);
                    UserInfoUI.getInstance().setDeleteUser(true);
                }
                else if(input.trim().matches("(?i)sort\\s+by\\s+alphabet") && currentMenu.equals(UserInfoUI.getInstance()))
                {
                    UserInfoUI.getInstance().setManagerUsers(true);
                    Control.getInstance().sortHashmap(Account.getAllAccounts());
                }
                else if(input.trim().matches("(?i)create\\s+manager\\s+profile"))
                {
                    UserInfoUI.getInstance().setCreateManagerProfile(true);
                }
            }
        }
        else if(input.trim().matches("(?i)back"))
        {
            if(seenPages.size()<=1)
                errorInput("you can't get back from here!" , currentMenu);
            currentMenu = seenPages.get(seenPages.size()-2);
            seenPages.set(seenPages.size()-1 , null);
        }
        else if(input.trim().equalsIgnoreCase("help"))
        {
            currentMenu.help();
            processInput(scanner.nextLine());
        }
        else
        {
            errorInput("Wrong Input",currentMenu);
        }
        this.run(currentMenu);
    }
    public void errorInput(String message,UI landing)
    {
        System.out.println(message);
        processInput(scanner.nextLine());
        goToNextPage(landing);
    }
    public void run(UI menu)
    {
        menu.run();
        System.out.println("For commands on this page please enter Help.");
        processInput(scanner.nextLine());
    }

}
