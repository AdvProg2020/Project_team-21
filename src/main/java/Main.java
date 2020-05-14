import Model.Account;
import View.ConsoleView;
import View.MainMenuUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
        ConsoleView.getInstance().processInput("main menu");
    }
}
