package View;

public class ProductInterface extends Menu {
    private static ProductInterface instance;
    private ProductInterface()
    {

    }

    public static ProductInterface getInstance() {
        if(instance == null)
            instance = new ProductInterface();
        return instance;
    }
}
