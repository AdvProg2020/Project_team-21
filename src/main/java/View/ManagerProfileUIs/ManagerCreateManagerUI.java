package View.ManagerProfileUIs;

import Controller.Control;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class ManagerCreateManagerUI extends UI {
    private static ManagerCreateManagerUI instance;
    private ManagerCreateManagerUI()
    {

    }

    public static ManagerCreateManagerUI getInstance()
    {
        if(instance == null)
            instance = new ManagerCreateManagerUI();
        return instance;
    }


    @Override
    public void run() {
        try {
            Scanner scanner = ConsoleView.getScanner();
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            System.out.println("Re-Enter password: ");
            String checkPassword = scanner.nextLine();
            System.out.println("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter email: ");
            String email = scanner.nextLine();
            System.out.println("Enter phone number: ");
            String phone = scanner.nextLine();
            Control.getInstance().createAccount("manager",username,password,firstName,lastName,email,phone,checkPassword,"",false);
            System.out.println("The account has been created!");
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }catch (Exception e) {
            ConsoleView.getInstance().errorInput(e.getMessage(), ConsoleView.getInstance().getLastMenu());
        }

    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}
