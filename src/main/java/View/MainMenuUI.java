package View;

import Controller.Control;
import Model.Account;

public class MainMenuUI extends UI {
    private static MainMenuUI instance;
    Control controller = Control.getInstance();
    private Account user = controller.getUser();
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
        System.out.println("       Most Popular Products By Our Users     ");
        controller.showPopularProducts();
        System.out.println("        Recent Sales For you        ");
        controller.showSales();
        ConsoleView.getInstance().processInput(ConsoleView.getScanner().nextLine());
    }
}
