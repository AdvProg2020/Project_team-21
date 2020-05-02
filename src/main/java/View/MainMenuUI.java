package View;

import Controller.Control;
import Model.Account;

public class MainMenuUI extends UI {
    private static MainMenuUI instance;
    private Account user = Control.getInstance().getUser();
    private MainMenuUI()
    {

    }
    public static MainMenuUI getInstance() {
        if(instance == null)
            instance = new MainMenuUI();
        return instance;
    }

    @Override
    public void run() {
        if(user == null)
        {
            System.out.println("login/signup            Advanced Idiots Market inc.                     ");
        }
        else
        {
            System.out.println("welcome"+user.getFirstName()+"            Advanced Idiots Market inc.                     ");
        }
        ConsoleView.getInstance().processInput(ConsoleView.getScanner().nextLine());
    }
}
