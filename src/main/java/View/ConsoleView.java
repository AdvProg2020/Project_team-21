package View;

public class ConsoleView {
    private Menu currentMenu;
    private ConsoleView instance;
    private ConsoleView()
    {

    }
    public ConsoleView getInstance() {
        if(instance == null)
            instance = new ConsoleView();
        return instance;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
