import View.ConsoleView;
import View.MainMenuUI;

public class Main {
    public static void main(String[] args){
        ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
        ConsoleView.getInstance().processInput("main menu");
    }
}