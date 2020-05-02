package View;

public class UserSignupUI extends UI {
    private static UserSignupUI instance;

    private UserSignupUI()
    {

    }
    public static UserSignupUI getInstance() {
        if(instance == null)
            instance = new UserSignupUI();
        return instance;
    }

    @Override
    public void run() {

    }
}
