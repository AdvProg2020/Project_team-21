package View;

public class MainMenuUI extends UI {
    private static MainMenuUI instance;

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

    }
}
