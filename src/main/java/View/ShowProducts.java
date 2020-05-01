package View;

public class ShowProducts extends Menu {
    private static ShowProducts instance;
    private ShowProducts()
    {

    }

    public static ShowProducts getInstance() {
        if(instance == null)
            instance = new ShowProducts();
        return instance;
    }
}
