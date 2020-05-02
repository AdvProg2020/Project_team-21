package View;

import Controller.Control;
import Model.Account;
import Model.Customer;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView{
    private static Scanner scanner;
    private UI currentMenu;
    private UI previousMenu;
    private static ConsoleView instance;
    private Account user = Control.getInstance().getUser();
    private ArrayList<UI> seenPages;

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

    public void processInput(String input)
    {
        if(input.trim().matches("(?i)main\\s*menu"))
        {
            seenPages.add(currentMenu);
            currentMenu = MainMenuUI.getInstance();
        }
        else if(input.trim().matches("(?i)(login|signup)") && currentMenu.equals(MainMenuUI.getInstance()))
        {
            seenPages.add(currentMenu);
            currentMenu = UserInfoUI.getInstance();
        }
        else if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(.+)"))
        {
            if(user != null && !(user instanceof Customer))
            {
                errorInput("you can't make a new user");
            }
            else
            {
                seenPages.add(currentMenu);
                currentMenu = UserSignupUI.getInstance();
            }
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
    private void errorInput(String message)
    {
        System.out.println(message);
        processInput(scanner.nextLine());
    }
    public void run(UI menu)
    {
        menu.run();
    }

}
