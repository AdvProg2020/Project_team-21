package View;

public class UserInterface extends Menu {
    private static UserInterface instance;
    private UserInterface()
    {

    }

    public static UserInterface getInstance() {
        if(instance == null)
            instance = new UserInterface();
        return instance;
    }
}
