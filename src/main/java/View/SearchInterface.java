package View;

public class SearchInterface extends Menu {
    private static SearchInterface instance;

    private SearchInterface()
    {

    }


    public static SearchInterface getInstance() {
        if(instance == null)
            instance = new SearchInterface();
        return instance;
    }
}
