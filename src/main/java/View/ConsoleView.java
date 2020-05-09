package View;

import Controller.Control;
import Model.Account;
import Model.Customer;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView{
    private static Scanner scanner;
    private UI currentMenu;
    private ArrayList<UI> seenPages;
    private static ConsoleView instance;
    private Account user = Control.getInstance().getUser();

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

    public void setCurrentMenu(UI currentMenu) {
        this.currentMenu = currentMenu;
    }

    public UI getCurrentMenu() {
        return currentMenu;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    private void goToNextPage(UI menu)
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
        else if(input.trim().matches("(?i)(login|signup|view\\s*personal\\s*info)"))
        {
            goToNextPage(UserInfoUI.getInstance());
        }
        else if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(.+)") && currentMenu.equals(UserLoginUI.getInstance()))
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
        else if(input.trim().matches("(?i)login\\s+(.+)") && currentMenu.equals(UserSignupUI.getInstance()))
        {
            UserLoginUI.getInstance().login(input.trim().split("\\s+")[1]);
            goToNextPage(UserInfoUI.getInstance());
        }
        else if(input.trim().matches("(?i)back"))
        {
            currentMenu = seenPages.get(seenPages.size()-1);
            seenPages.set(seenPages.size()-1 , null);
        }
        else
        {
            errorInput("Wrong Input");
        }
        this.run(currentMenu);
    }
    public void errorInput(String message)
    {
        System.out.println(message);
        processInput(scanner.nextLine());
    }
    public void run(UI menu)
    {
        menu.run();
        processInput(scanner.nextLine());
    }

}
