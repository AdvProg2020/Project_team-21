package View.ManagerProfileUIs.ManageCategories;

import Model.Category;
import Model.Product;
import View.ConsoleView;
import View.UI;

public class ManagerRemoveProductFromCategoryUI extends UI {
    private static ManagerRemoveProductFromCategoryUI instance;
    private ManagerRemoveProductFromCategoryUI()
    {

    }

    public static ManagerRemoveProductFromCategoryUI getInstance()
    {
        if(instance == null)
            instance = new ManagerRemoveProductFromCategoryUI();
        return instance;
    }
    String productId;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public void run()
    {
        if(!Product.getAllProducts().containsKey(productId))
        {
            ConsoleView.getInstance().errorInput("This product doesn't exist!", ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            for (Category category : Category.getAllCategories()) {
                if(category.getName().equalsIgnoreCase(ManagerEditCategoryUI.getInstance().getCategoryName()))
                {
                    category.removeProductFromCategory(Product.getAllProducts().get(productId));
                }
            }
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }

}
