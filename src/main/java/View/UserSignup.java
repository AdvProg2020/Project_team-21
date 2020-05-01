package View;

public class UserSignup extends Menu {
    private static UserSignup instance;
    private UserSignup()
    {

    }
    public static UserSignup getInstance() {
        if(instance == null)
            instance = new UserSignup();
        return instance;
    }
}
