package View;

public class ProductsUI extends UI {
    private static ProductsUI instance;

    private ProductsUI()
    {

    }
    public static ProductsUI getInstance() {
        if(instance == null)
            instance = new ProductsUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println("Hello!");
    }

    @Override
    public void help() {

    }
}
