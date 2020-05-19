package View.ProductsUIs.ProductsMainUI;

import View.UI;

import Model.Product;

public class ProductsMainUI extends UI {
    private static ProductsMainUI instance;

    private ProductsMainUI()
    {

    }
    public static ProductsMainUI getInstance() {
        if(instance == null)
            instance = new ProductsMainUI();
        return instance;
    }

    @Override
    public void run() {
        System.out.println(Product.getAllProducts());
    }

    @Override
    public void help() {
        System.out.println("view categories");
        System.out.println("filtering");
        System.out.println("sorting");
        System.out.println("show products");
        System.out.println("show product [Product ID]");
    }
}