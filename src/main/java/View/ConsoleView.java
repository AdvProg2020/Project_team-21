package View;

import Model.Account;
import Model.Customer;
import com.sun.tools.javac.Main;

import java.util.Scanner;

public class ConsoleView{
    private static Scanner scanner;
    private UI currentMenu;
    private UI previousMenu;
    private static ConsoleView instance;
    private Account user;

    private ConsoleView()
    {
        scanner = new Scanner(System.in);
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
        if(input.trim().matches("(?i)main\\s*menu")){
            previousMenu = currentMenu;
            currentMenu = MainMenuUI.getInstance();
        }else if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(.+)")){
            if(user != null && user instanceof Customer == false)
            {
                errorInput("you can't make a new user");
            }
            else
            {

            }
        }else if(input.trim().matches("(?i)main\\s*menu")){

        }else{
           errorInput("wrong input");
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
