package View;

public class UserInfo extends Menu {
    private static UserInfo instance;
    private UserInfo()
    {

    }

    public static UserInfo getInstance() {
        if(instance == null)
            instance = new UserInfo();
        return instance;
    }
}
