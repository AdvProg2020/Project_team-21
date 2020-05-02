package View;

public class SearchUI extends UI {
    private static SearchUI instance;

    private SearchUI()
    {

    }
    public static SearchUI getInstance() {
        if(instance == null)
            instance = new SearchUI();
        return instance;
    }

    @Override
    public void run() {

    }
}
