package View.ManagerProfileUIs.ManageCategories;

import Controller.ControlManager;
import Model.Product;
import View.ConsoleView;
import View.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class ManagerAddCategoryUI extends UI {
    private static ManagerAddCategoryUI instance;
    private ManagerAddCategoryUI()
    {

    }

    public static ManagerAddCategoryUI getInstance()
    {
        if(instance == null)
            instance = new ManagerAddCategoryUI();
        return instance;
    }
    private String categoryName;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public void run()
    {
        ArrayList<Product> products = new ArrayList<>();
        Scanner scanner = ConsoleView.getScanner();
        String productId = "";
        try
        {
            while (!productId.equalsIgnoreCase("end"))
            {
                System.out.println("Enter a product's ID you want to add to this category or to end type [end]: ");
                productId = scanner.nextLine();
                if(Product.getAllProducts().containsKey(productId))
                {
                    products.add(Product.getAllProducts().get(productId));
                }
                else
                {
                    System.out.println("This product doesn't exist!");
                }
            }
            ControlManager.getInstance().addCategory(categoryName,products);
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }catch (Exception e)
        {
            ConsoleView.getInstance().errorInput("This category doesn't exist!",ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

    @Override
    public void sort() {

    }
}
