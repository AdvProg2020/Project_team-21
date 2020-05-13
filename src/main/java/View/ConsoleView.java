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
    public UI getLastMenu()
    {
        return seenPages.get(seenPages.size()-2);
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
        else if(input.trim().equalsIgnoreCase("help"))
        {
            currentMenu.help();
            processInput(scanner.nextLine());
        }
        else if(input.trim().matches("(?i)(login|signup|view\\s*personal\\s*info)"))
        {
            goToNextPage(UserInfoUI.getInstance());
        }
        else if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(\\S+)") && currentMenu.equals(UserLoginUI.getInstance()))
        {
                UserSignupUI.getInstance().setType(input.trim().split("\\s+")[2]);
                UserSignupUI.getInstance().setUserName(input.trim().split("\\s+")[3]);
                goToNextPage(UserSignupUI.getInstance());
        }
        else if(input.trim().matches("(?i)login\\s+(.+)") && currentMenu.equals(UserSignupUI.getInstance()))
        {
            UserLoginUI.getInstance().login(input.trim().split("\\s+")[1]);
            goToNextPage(UserInfoUI.getInstance());
        }
        else if(input.trim().matches("(?i)back"))
        {
            if(seenPages.size()<=1)
                errorInput("you can't get back from here!" , currentMenu);
            currentMenu = seenPages.get(seenPages.size()-2);
            seenPages.set(seenPages.size()-1 , null);
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
        currentMenu = landing;
    }
    public void run(UI menu)
    {
        menu.run();
        System.out.println("For commands on this page please enter Help.");
        processInput(scanner.nextLine());
    }

}
