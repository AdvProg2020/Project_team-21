package View;

public class ProductInfoUI extends UI {
    private static ProductInfoUI instance;

    private ProductInfoUI()
    {

    }
    public static ProductInfoUI getInstance() {
        if(instance == null)
            instance = new ProductInfoUI();
        return instance;
    }

    @Override
    public void run() {

    }
}
