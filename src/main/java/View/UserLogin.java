package View;

public class UserLogin extends Menu {
    private static UserLogin instance;
    private UserLogin()
    {

    }

    public static UserLogin getInstance() {
        if(instance == null)
            instance = new UserLogin();
        return instance;
    }
}
