package View;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProductsUI extends UI {
    private static ProductsUI instance;

    public ProductsUI(String name, UI parentUI) {
        super("Products UI", parentUI);
        addToSubMenus(1 , this.getViewAllCategoriesUI());
//        addToSubMenus(2 , FilterUI);
//        addToSubMenus(3, SortUI);
        addToSubMenus(4 , this.getShowProductsUI());
        addToSubMenus(5, this.getShowProductsUI());
    }

    private UI getViewAllCategoriesUI() {

        return new UI("View All Categories UI", this) {
            @Override
            public void show() {
                System.out.println("All Categories are: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                try {
                    Matcher matcher = getMatcher(input, "^\\s*back\\s*$");
                    if (matcher.find()) {
                        parentUI.show();
                        parentUI.execute();
                        return;
                    } else
                        throw new Exception("Invalid Input");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };

    }

    private UI getShowProductsUI() {

        return new UI("Show Products Menu", this) {
            @Override
            public void show() {
                System.out.println("All Products are: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                try {
                    Matcher matcher = getMatcher(input, "^\\s*back\\s*$");
                    if (matcher.find()) {
                        parentUI.show();
                        parentUI.execute();
                        return;
                    } else
                        throw new Exception("Invalid Input");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    private UI getProductMenu() {

        return new UI("Product Menu", this) {
            @Override
            public void show() {
                System.out.println("Please enter productID: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                try {
                    Matcher m1 = getMatcher(input, "");
                    Matcher m2 = getMatcher(input, "^\\s*back\\s*$");
                    if (m2.find()) {
                        parentUI.show();
                        parentUI.execute();
                        return;
                    } else if (!m1.find())
                        throw new Exception("Invalid Input");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

//    public static ProductsUI getInstance() {
//        if(instance == null)
//            instance = new ProductsUI();
//        return instance;
//    }

    @Override
    public void run() {

    }
}

class SortUI extends UI{
    public SortUI(String name, UI parentUI) {
        super("SortUI", parentUI);
        super.addToSubMenus(1, this.getAvailableSorts());
        super.addToSubMenus(2,this.getSortUI());
        super.addToSubMenus(3,this.getCurrentSortUI());
        super.addToSubMenus(4,this.getDisableSortUI());
    }

    @Override
    public void run() {

    }

    private UI getAvailableSorts(){
        return new UI("Show availble sorts" , this){
            @Override
            public void show() {
                System.out.println("Available sorts are: ");
            }

            @Override
            public void execute() {
                String input=scanner.nextLine();
                try {
                    Matcher matcher = getMatcher(input,"\\s*back\\s*$");
                    if (matcher.find()){
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else {
                        throw new Exception("Invalid input");
                    }
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    private UI getSortUI(){
        return new UI ("Sort Menu",this){
            @Override
            public void show() {
                System.out.println("Please enter an available sort:");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                try
                {
                    Matcher m1 = getMatcher(input, "");
                    Matcher m2 = getMatcher(input, "^\\s*back\\s*$");
                    if(m2.find())
                    {
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else if(!m1.find())
                    {
                        throw new Exception("invalid input");
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    private UI getCurrentSortUI(){
        return new UI ("Current Sort Menu",this){
            @Override
            public void show() {
                System.out.println("Current sort is:\n(Enter back to return)");
            }

            @Override
            public void execute() {
                // sort products
                String input = scanner.nextLine();
                try
                {
                    Matcher matcher2 = getMatcher(input, "^\\s*back\\s*$");
                    if(matcher2.find())
                    {
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else
                        throw new Exception("Invalid Input");
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    private UI getDisableSortUI(){
        return new UI("Disable Sort Menu", this) {
            @Override
            public void show() {
                System.out.println("Enter back to return");
            }

            @Override
            public void execute() {
                // sort haro ignore kone
                String input = scanner.nextLine();
                try
                {
                    Matcher matcher2 = getMatcher(input, "^\\s*back\\s*$");
                    if(matcher2.find())
                    {
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else
                        throw new Exception("Invalid Input");
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }
}

class FilteringMenu extends UI {

    public FilteringMenu(UI parentMenu) {
        super("Filtering Menu", parentMenu);
        super.addToSubMenus(1, this.getShowAvailableFiltersMenu());
        super.addToSubMenus(2, this.getAddFilterMenu());
        super.addToSubMenus(3, this.getCurrentFiltersMenu());
        super.addToSubMenus(4, this.getDisableFilterMenu());
    }

    private UI getShowAvailableFiltersMenu()
    {
        return new UI("Show Available Filters Menu", this) {
            @Override
            public void show() {
                System.out.println("Available filters are:\n(Enter back to return)");
            }

            @Override
            public void execute() {
//                System.out.println(AllProductManager.showFilterOption());
                String input = scanner.nextLine();
                try
                {
                    Matcher matcher2 = getMatcher(input, "^\\s*back\\s*$");
                    if(matcher2.find())
                    {
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else
                        throw new Exception("Invalid Input");
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    private UI getAddFilterMenu()
    {
        return new UI("Add Filters Menu", this) {
            @Override
            public void show() {
                System.out.println("Please enter filter to add:(Enter back to return)");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                try
                {
                    Matcher matcher1 = getMatcher(input, "^\\s*sellerUsername\\s+(\\S+)\\s*$");
                    Matcher matcher2 = getMatcher(input, "^\\s*rangeOfPrice\\s+(\\d+)\\s+(\\d+)\\s*$");
                    Matcher matcher3 = getMatcher(input, "^\\s*categoryName\\s+(\\S+)\\s*$");
                    Matcher matcher4 = getMatcher(input, "^\\s*available\\s*$");
                    Matcher matcher5 = getMatcher(input, "^\\s*rangeOfScore\\s+(\\d[\\.\\d+]?)$");
                    Matcher matcher6 = getMatcher(input, "^\\s*back\\s*$");
                    if(matcher6.find())
                    {
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else if(matcher1.find())
                    {
//                        System.out.println(AllProductManager.addFilterOption("sellerUsername " + matcher1.group(1)));
                    }
                    else if(matcher2.find())
                    {
//                        System.out.println(AllProductManager.addFilterOption("rangeOfPrice " + matcher2.group(1) + " " + matcher2.group(2)));
                    }
                    else if(matcher3.find())
                    {
//                        System.out.println(AllProductManager.addFilterOption("categoryName " + matcher3.group(1)));
                    }
                    else if(matcher4.find())
                    {
//                        System.out.println(AllProductManager.addFilterOption("available"));
                    }
                    else if(matcher5.find())
                    {
//                        System.out.println(AllProductManager.addFilterOption("rangeOfScore " + matcher5.group(1)));
                    }
                    else
                    {
                        throw new Exception("invalid input");
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    private UI getCurrentFiltersMenu()
    {
        return new UI("Current Filters Menu", this) {
            @Override
            public void show() {
                System.out.println("Current filters are:\n(Enter back to return)");
            }

            @Override
            public void execute() {
//                System.out.println(AllProductManager.getFilterOptions());
                String input = scanner.nextLine();
                try
                {
                    Matcher matcher2 = getMatcher(input, "^\\s*back\\s*$");
                    if(matcher2.find())
                    {
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else
                        throw new Exception("Invalid Input");
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    private UI getDisableFilterMenu()
    {
        return new UI("Disable Filter Menu", this) {
            @Override
            public void show() {
                System.out.println("Please enter filter to disable:(Enter back to return)");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                try
                {
                    Matcher matcher2 = getMatcher(input, "^\\s*back\\s*$");
                    if(matcher2.find())
                    {
                        this.parentUI.show();
                        this.parentUI.execute();
                        return;
                    }
                    else
                    {
//                        System.out.println(AllProductManager.removeFilterOption(input));
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
                this.execute();
            }

            @Override
            public void run() {

            }
        };
    }

    @Override
    public void run() {

    }
}