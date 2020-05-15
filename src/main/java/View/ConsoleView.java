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
    private boolean rightInput = false;

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
        if(seenPages.size()>1)
            return seenPages.get(seenPages.size()-2);
        return currentMenu;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public void goToNextPage(UI menu)
    {
        seenPages.add(menu);
        currentMenu = menu;
    }
    public void processInput(String input)
    {
        user = Control.getInstance().getUser();
        if(input.trim().equalsIgnoreCase("current"))
        {
            System.out.println(currentMenu.toString());
        }
        if(input.trim().matches("(?i)main\\s*menu"))
        {
            goToNextPage(MainMenuUI.getInstance());
            rightInput = true;
        }
        else if(input.trim().matches("(?i)(login|signup|(view\\s*personal\\s*info))"))
        {
            UserInfoUI.getInstance().setNewToInfo(true);
            goToNextPage(UserInfoUI.getInstance());
            rightInput = true;
        }
        else if(input.trim().matches("(?i)sign\\s*out"))
        {
            if(Control.getInstance().getUser() == null)
            {
                errorInput("You're not logged in!" , currentMenu);
            }
            else{
                System.out.println("Goodbye " + user.getFirstName());
                Control.getInstance().setUser(null);
            }
        }
        if(currentMenu.equals(UserLoginUI.getInstance()))
        {
            if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(\\S+)"))
            {
                UserSignupUI.getInstance().setType(input.trim().split("\\s+")[2]);
                UserSignupUI.getInstance().setUserName(input.trim().split("\\s+")[3]);
                goToNextPage(UserSignupUI.getInstance());
                rightInput = true;
             }
            else if(input.trim().matches("(?i)login\\s+(\\S+)"))
            {
                UserLoginUI.getInstance().login(input.trim().split("\\s+")[1]);
                 goToNextPage(UserInfoUI.getInstance());
                 rightInput = true;
            }
        }
        else if(currentMenu.equals(UserInfoUI.getInstance()))
        {
            if(input.trim().matches("(?i)show\\s+password") && currentMenu.equals(UserInfoUI.getInstance()))
            {
                UserInfoUI.getInstance().setShowPassword(true);
                rightInput = true;
            }
            else if(input.trim().matches("(?i)edit\\s+(.+)"))
            {
                UserInfoUI.getInstance().setEditField(true);
                UserInfoUI.getInstance().setEditFieldField(input.split("\\s+")[1]);
                rightInput = true;
            }
            else if(user instanceof Manager)
            {
                if(input.trim().matches("(?i)manage\\s+users"))
                {
                    goToNextPage(ManagerManageUsersUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)manage\\s+all\\s+products"))
                {
                    goToNextPage(ManagerManageProductsUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)create\\s+discount\\s+code"))
                {
                    goToNextPage(ManagerCreateDiscountCodeUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches(("(?i)view\\s+discount\\s+codes")))
                {
                    goToNextPage(ManagerViewDiscountCodesUI.getInstance());
                    rightInput = true;
                }
            }
        }
        else if(currentMenu.equals(ManagerViewDiscountCodesUI.getInstance()))
        {
            if(input.trim().matches("(?i)view\\s+discount\\s+code\\s+(\\S+)"))
            {
                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)sort\\s+by\\s+alphabet\\s+(\\S+)"))
            {
                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)view\\s+discount\\s+code\\s+(\\S+)"))
            {
                ManagerViewDiscountCodeUI.getInstance().setCode(input.split("\\s+")[3]);
                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerManageProductsUI.getInstance()))
        {
            if(input.trim().matches("(?i)remove\\s+(.+)"))
            {
                ManagerRemoveProductUI.getInstance().setID(input.split("\\s+")[1]);
                goToNextPage(ManagerManageProductsUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerManageUsersUI.getInstance()))
        {
            if(input.trim().matches("(?i)view\\s+(\\S+)"))
            {
                goToNextPage(ManagerViewUI.getInstance());
                ManagerViewUI.getInstance().setUsername(input.split("\\s+")[1]);
                rightInput = true;
            }
            else if(input.trim().matches("(?i)delete\\s+user\\s+(\\S+)"))
            {
                goToNextPage(ManagerDeleteUserUI.getInstance());
                ManagerDeleteUserUI.getInstance().setUsername(input.split("\\s+")[2]);
                rightInput = true;
            }
            else if(input.trim().matches("(?i)sort\\s+by\\s+alphabet"))
            {
                ManagerManageUsersUI.getInstance().sort();
                rightInput = true;
            }
            else if(input.trim().matches("(?i)create\\s+manager\\s+profile"))
            {
                goToNextPage(ManagerCreateManagerUI.getInstance());
                rightInput = true;
            }
        }
        if(input.trim().matches("(?i)back"))
        {
            if(currentMenu.equals(UserLoginUI.getInstance()))
            {
                currentMenu = getLandingPageAfterSigninOrSignup();
            }
            else {
                if(seenPages.size()<=1)
                    errorInput("you can't get back from here!" , currentMenu);
                currentMenu = seenPages.get(seenPages.size()-2);
            }
            seenPages.remove(seenPages.size()-1);
            rightInput = true;
        }
        if(input.trim().equalsIgnoreCase("help"))
        {
            currentMenu.help();
            rightInput = true;
        }
        if(!rightInput)
        {
            errorInput("Wrong Input",currentMenu);
        }
        rightInput = false;
        this.run(currentMenu);
    }
    public void errorInput(String message,UI landing)
    {
        System.out.println(message);
        goToNextPage(landing);
    }
    public void run(UI menu)
    {
        menu.run();
        System.out.println("For commands on this page please enter Help.");
        processInput(scanner.nextLine());
    }

}
