package View;

public class SalesUI extends UI {
    private static SalesUI instance;

    private SalesUI()
    {

    }
    public static SalesUI getInstance() {
        if(instance == null)
            instance = new SalesUI();
        return instance;
    }

    @Override
    public void run() {

    }

    @Override
    public void help() {

    }
}
