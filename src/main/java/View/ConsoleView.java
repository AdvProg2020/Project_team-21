package View;

import Controller.Control;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.SaveData;
import Model.Category;
import Model.ShoppingCart;
import View.CustomerProfileUIs.CustomerCartUIs.CustomerCartShowProductsUI;
import View.CustomerProfileUIs.CustomerCartUIs.CustomerIncreaseDecreaseProductCartUI;
import View.CustomerProfileUIs.CustomerCartUIs.CustomerShowTotalPriceCartUI;
import View.CustomerProfileUIs.CustomerCartUIs.CustomerViewCartUI;
import View.CustomerProfileUIs.CustomerOrdersUIs.CustomerOrderInfoUI;
import View.CustomerProfileUIs.CustomerOrdersUIs.CustomerRateProductUI;
import View.CustomerProfileUIs.CustomerPurchaseUI;
import View.CustomerProfileUIs.CustomerOrdersUIs.CustomerViewOrdersUI;
import View.CustomerProfileUIs.CustomerViewBalanceUI;
import View.ManagerProfileUIs.ManageCategories.*;
import View.ManagerProfileUIs.ManageDiscountCodes.*;
import View.ManagerProfileUIs.ManageProducts.ManagerManageProductsUI;
import View.ManagerProfileUIs.ManageProducts.ManagerRemoveProductUI;
import View.ManagerProfileUIs.ManageRequests.ManagerAcceptRequestUI;
import View.ManagerProfileUIs.ManageRequests.ManagerDeclineRequestUI;
import View.ManagerProfileUIs.ManageRequests.ManagerDetailsRequestUI;
import View.ManagerProfileUIs.ManageRequests.ManagerRequestsUI;
import View.ManagerProfileUIs.ManageUsers.ManagerCreateManagerUI;
import View.ManagerProfileUIs.ManageUsers.ManagerDeleteUserUI;
import View.ManagerProfileUIs.ManageUsers.ManagerManageUsersUI;
import View.ManagerProfileUIs.ManageUsers.ManagerViewUI;
import View.OffsUI.FilterinUIForOffs.*;
import View.OffsUI.OffsMainUI.OffsUI;
import View.OffsUI.OffsMainUI.ShowProductsWithOffAfterUI;
import View.ProductPageUI.ProductDigestUI;
import View.ProductPageUI.ProductMainUI;
import View.ManagerProfileUIs.ManagerAddBalanceUI;
//import View.OffsUI.OffsUI;
import View.ManagerProfileUIs.SortDiscountCodesType;
import View.ProductPageUI.*;
import View.SellerProfileUIs.*;
import View.SellerProfileUIs.ManageOffs.SellerAddOffUI;
import View.SellerProfileUIs.ManageOffs.SellerEditOffUI;
import View.SellerProfileUIs.ManageOffs.SellerViewOffInfoUI;
import View.SellerProfileUIs.ManageOffs.SellerViewOffsUI;
import View.SellerProfileUIs.ManageProducts.SellerEditProductUI;
import View.SellerProfileUIs.ManageProducts.SellerManageProductsUI;
import View.SellerProfileUIs.ManageProducts.SellerViewProductBuyersUI;
import View.SellerProfileUIs.ManageProducts.SellerViewProductUI;
import View.ProductsUIs.FilteringUI.*;
import View.ProductsUIs.ProductsMainUI.ProductsMainUI;
import View.ProductsUIs.ProductsMainUI.ShowProductsAfterUI;
import View.ProductsUIs.ProductsMainUI.ViewCategoriesUI;
import View.ProductsUIs.SortingUI.*;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView{
    private static Scanner scanner;
    private UI currentMenu;
    private ArrayList<UI> seenPages;
    private static ConsoleView instance;
    private Account user = Control.getInstance().getUser();
    private UI landingPageAfterSigninOrSignup;
    private boolean rightInput = false;

    private ConsoleView()
    {
        scanner = new Scanner(System.in);
        seenPages = new ArrayList<>();
    }
    public static ConsoleView getInstance() {
        if(instance == null)
            instance = new ConsoleView();
        return instance;
    }

    public UI getLandingPageAfterSigninOrSignup() {
        return landingPageAfterSigninOrSignup;
    }

    public void setLandingPageAfterSigninOrSignup(UI landingPageAfterSigninOrSignup) {
        this.landingPageAfterSigninOrSignup = landingPageAfterSigninOrSignup;
    }

    public void setCurrentMenu(UI currentMenu) {
        this.currentMenu = currentMenu;
    }

    public UI getCurrentMenu() {
        return currentMenu;
    }
    public UI getLastMenu()
    {
        if(seenPages.size()>1)
            return seenPages.get(seenPages.size()-2);
        return currentMenu;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public void goToNextPage(UI menu)
    {
        seenPages.add(menu);
        currentMenu = menu;
    }
    public void processInput(String input)
    {
        user = Control.getInstance().getUser();
        if(input.trim().equalsIgnoreCase("current"))
        {
            System.out.println(currentMenu.toString());
        }
        if(input.trim().matches("(?i)main\\s*menu"))
        {
            goToNextPage(MainMenuUI.getInstance());
            rightInput = true;
        }
        else if(input.trim().matches("(?i)(login|signup|(view\\s*personal\\s*info))"))
        {
            UserInfoUI.getInstance().setNewToInfo(true);
            goToNextPage(UserInfoUI.getInstance());
            rightInput = true;
        }
        else if(input.trim().matches("(?i)sign\\s*out"))
        {
            if(Control.getInstance().getUser() == null)
            {
                errorInput("You're not logged in!" , currentMenu);
            }
            else{
                System.out.println("Goodbye " + user.getFirstName());
                Control.getInstance().setUser(null);
                Control.getInstance().setSignOutCart(new ShoppingCart(null));
                goToNextPage(MainMenuUI.getInstance());
            }
            rightInput = true;
        }
        if(currentMenu.equals(UserLoginUI.getInstance()))
        {
            if(input.trim().matches("(?i)create account\\s+(customer|manager|seller)\\s+(\\S+)"))
            {
                UserSignupUI.getInstance().setType(input.trim().split("\\s+")[2]);
                UserSignupUI.getInstance().setUserName(input.trim().split("\\s+")[3]);
                goToNextPage(UserSignupUI.getInstance());
                rightInput = true;
             }
            else if(input.trim().matches("(?i)login\\s+(\\S+)"))
            {
                UserLoginUI.getInstance().login(input.trim().split("\\s+")[1]);
                 goToNextPage(UserInfoUI.getInstance());
                 rightInput = true;
            }
        }
        else if(currentMenu.equals(MainMenuUI.getInstance()))
        {
            if(input.trim().matches("(?i)products"))
            {
                goToNextPage(ProductsMainUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)categories"))
            {
                for (Category category : Category.getAllCategories()) {
                    System.out.println(category.getName());
                }
                rightInput = true;
            }
            else if(input.trim().matches("(?i)offs"))
            {
               goToNextPage(OffsUI.getInstance());
               rightInput = true;
            }
        }
        else if(currentMenu.equals(ProductMainUI.getInstance()))
        {
            if(input.trim().matches("(?i)digest"))
            {
                goToNextPage(ProductDigestUI.getInstance());
                rightInput = true;
            }
//            else if(input.trim().matches("(?i)attributes"))
//            {
//                goToNextPage(ProductAttributesUI.getInstance());
//                rightInput = true;
//            }
            else if(input.trim().matches("(?i)compare\\s+(.+)"))
            {
                ProductCompareUI.getInstance().setCompareProductID(input.split("\\s+")[1]);
                goToNextPage(ProductCompareUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)comments"))
            {
                goToNextPage(ProductCommentsUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ProductCommentsUI.getInstance()))
        {
            if(input.trim().matches("(?i)add\\s+comment"))
            {
                goToNextPage(ProductAddToCartUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ProductDigestUI.getInstance()))
        {
            if(input.trim().matches("(?i)add\\s+to\\s+cart"))
            {
                goToNextPage(ProductAddToCartUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(UserInfoUI.getInstance()))
        {
            if(input.trim().matches("(?i)show\\s+password") && currentMenu.equals(UserInfoUI.getInstance()))
            {
                UserInfoUI.getInstance().setShowPassword(true);
                rightInput = true;
            }
            else if(input.trim().matches("(?i)edit\\s+(.+)"))
            {
                UserInfoUI.getInstance().setEditField(true);
                UserInfoUI.getInstance().setEditFieldField(input.split("\\s+")[1]);
                rightInput = true;
            }
            else if(user instanceof Manager)
            {
                if(input.trim().matches("(?i)manage\\s+users"))
                {
                    goToNextPage(ManagerManageUsersUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)manage\\s+all\\s+products"))
                {
                    goToNextPage(ManagerManageProductsUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)create\\s+discount\\s+code"))
                {
                    goToNextPage(ManagerCreateDiscountCodeUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches(("(?i)view\\s+discount\\s+codes")))
                {
                    goToNextPage(ManagerViewDiscountCodesUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches(("(?i)manage\\s+requests")))
                {
                    goToNextPage(ManagerRequestsUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)manage\\s+categories"))
                {
                    goToNextPage(ManagerManageCategoriesUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)add\\s+balance"))
                {
                    goToNextPage(ManagerAddBalanceUI.getInstance());
                    rightInput = true;
                }
            }
            else if(user instanceof Seller)
            {
                if(input.trim().matches("(?i)view\\s+company\\s+information"))
                {
                    goToNextPage(SellerViewCompanyUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)view\\s+sales\\s+history"))
                {
                    goToNextPage(SellerViewSalesUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)view\\s+sales\\s+history\\s+"))
                {
                    goToNextPage(SellerViewSalesUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)sort\\s+sales\\s+history"))
                {
                    SellerViewSalesUI.getInstance().sort();
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)manage\\s+products"))
                {
                    goToNextPage(SellerManageProductsUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)add\\s+product"))
                {
                    goToNextPage(SellerAddProductReqUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)remove\\s+product\\s+(.+)"))
                {
                    SellerRemoveProductReqUI.getInstance().setProductID(input.split("\\s+")[2]);
                    goToNextPage(SellerRemoveProductReqUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)show\\s+categories"))
                {
                    goToNextPage(SellerShowCategoriesUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)view\\s+offs"))
                {
                    goToNextPage(SellerViewOffsUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)sort\\s+offs\\s+by\\s+offId"))
                {
                    SellerViewOffsUI.getInstance().setSortOffType(SortOffType.OFF_ID);
                    SellerViewOffsUI.getInstance().sort();
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)sort\\s+offs\\s+by\\s+off\\s+amount"))
                {
                    SellerViewOffsUI.getInstance().setSortOffType(SortOffType.OFF_AMOUNT);
                    SellerViewOffsUI.getInstance().sort();
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)view\\s+balance"))
                {
                    goToNextPage(SellerViewBalanceUI.getInstance());
                    rightInput = true;
                }
            }
            else if(user instanceof Customer)
            {
                if(input.trim().matches("(?i)view\\s+cart"))
                {
                    goToNextPage(CustomerViewCartUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)view\\s+orders"))
                {
                    goToNextPage(CustomerViewOrdersUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)sort\\s+orders"))
                {
                    CustomerViewOrdersUI.getInstance().sort();
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)view\\s+balance"))
                {
                    goToNextPage(CustomerViewBalanceUI.getInstance());
                    rightInput = true;
                }
                else if(input.trim().matches("(?i)view\\s+discount\\s+codes"))
                {
                    goToNextPage(CustomerViewBalanceUI.getInstance());
                    rightInput = true;
                }
            }
        }
        else if(currentMenu.equals(CustomerViewOrdersUI.getInstance()))
        {
            if(input.trim().matches("(?i)show\\s+order\\s+(.+)"))
            {
                CustomerOrderInfoUI.getInstance().setOrderID(input.split("\\s+")[2]);
                goToNextPage(CustomerOrderInfoUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)rate\\s+(.+)\\s+(.+)"))
            {
                CustomerRateProductUI.getInstance().setProductID(input.split("\\s+")[1]);
                CustomerRateProductUI.getInstance().setScore(input.split("\\s+")[2]);
                goToNextPage(CustomerRateProductUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(CustomerViewCartUI.getInstance()))
        {
            if(input.trim().matches("(?i)show\\s+products"))
            {
                goToNextPage(CustomerCartShowProductsUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)view\\s+(.+)"))
            {
                goToNextPage(ProductMainUI.getInstance());
                ProductMainUI.getInstance().setProductID(input.split("\\s+")[1]);
                rightInput = true;
            }
            else if(input.trim().matches("(?i)(increase|decrease)\\s+(.+)"))
            {
                CustomerIncreaseDecreaseProductCartUI.getInstance().setProductID(input.split("\\s+")[1]);
                if(input.split("\\s+")[0].equalsIgnoreCase("increase"))
                    CustomerIncreaseDecreaseProductCartUI.getInstance().setIncrease(true);
                else
                    CustomerIncreaseDecreaseProductCartUI.getInstance().setIncrease(false);
                goToNextPage(CustomerIncreaseDecreaseProductCartUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)show\\s+total\\s+price"))
            {
                goToNextPage(CustomerShowTotalPriceCartUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)purchase"))
            {
                goToNextPage(CustomerPurchaseUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(SellerViewOffsUI.getInstance()))
        {
            if(input.trim().matches("(?i)view\\s+(.+)"))
            {
                SellerViewOffInfoUI.getInstance().setOffID(input.split("\\s+")[1]);
                goToNextPage(SellerViewOffInfoUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)edit\\s+(.+)"))
            {
                SellerEditOffUI.getInstance().setOffID(input.split("\\s+")[1]);
                goToNextPage(SellerEditOffUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)add\\s+off"))
            {
                goToNextPage(SellerAddOffUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(SellerManageProductsUI.getInstance()))
        {
            if(input.trim().matches("(?i)view\\s+(.+)"))
            {
                SellerViewProductUI.getInstance().setProductID(input.split("\\s+")[1]);
                goToNextPage(SellerViewProductUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)view\\s+buyers\\s+(.+)"))
            {
                SellerViewProductBuyersUI.getInstance().setProductID(input.split("\\s+")[2]);
                goToNextPage(SellerViewProductBuyersUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)sort\\s+buyers\\s+(.+)"))
            {
                SellerViewProductBuyersUI.getInstance().setProductID(input.split("\\s+")[2]);
                goToNextPage(SellerViewProductBuyersUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)edit\\s+(.+)"))
            {
                SellerEditProductUI.getInstance().setProductID(input.split("\\s+")[1]);
                goToNextPage(SellerEditProductUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerManageCategoriesUI.getInstance()))
        {
            if(input.trim().matches("(?i)edit\\s+(.+)"))
            {
                ManagerEditCategoryUI.getInstance().setCategoryName(input.split("\\s+")[1]);
                goToNextPage(ManagerEditCategoryUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)add\\s+(.+)"))
            {
                ManagerAddCategoryUI.getInstance().setCategoryName(input.split("\\s+")[1]);
                goToNextPage(ManagerAddCategoryUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)remove\\s+(.+)"))
            {
                ManagerRemoveCategoryUI.getInstance().setCategoryName(input.split("\\s+")[1]);
                goToNextPage(ManagerRemoveCategoryUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerEditCategoryUI.getInstance()))
        {
            if(input.trim().matches("(?i)name"))
            {
                goToNextPage(ManagerEditCategoryNameUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)add\\s+(.+)"))
            {
                ManagerAddProductToCategoryUI.getInstance().setProductId(input.split("\\s+")[1]);
                goToNextPage(ManagerAddProductToCategoryUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)remove\\s+(.+)"))
            {
                ManagerRemoveProductFromCategoryUI.getInstance().setProductId(input.split("\\s+")[1]);
                goToNextPage(ManagerRemoveProductFromCategoryUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerRequestsUI.getInstance()))
        {
            if(input.trim().matches("(?i)details\\s+(.+)"))
            {
                ManagerDetailsRequestUI.getInstance().setRequestId(input.split("\\s+")[1]);
                goToNextPage(ManagerDetailsRequestUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)accept\\s+(.+)"))
            {
                ManagerAcceptRequestUI.getInstance().setRequestId(input.split("\\s+")[1]);
                goToNextPage(ManagerAcceptRequestUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)decline\\s+(.+)"))
            {
                ManagerDeclineRequestUI.getInstance().setRequestId(input.split("\\s+")[1]);
                goToNextPage(ManagerDeclineRequestUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerViewDiscountCodesUI.getInstance()))
        {
            if(input.trim().matches("(?i)sort\\s+by\\s+percentage"))
            {
                ManagerViewDiscountCodeUI.getInstance().setSortDiscountCodesType(SortDiscountCodesType.DISCOUNT_PERCENTAGE);
                SellerViewOffsUI.getInstance().sort();
                rightInput = true;
            }
            else if(input.trim().matches("(?i)sort\\s+by\\s+discountI\\s+alphabet"))
            {
                ManagerViewDiscountCodeUI.getInstance().setSortDiscountCodesType(SortDiscountCodesType.DISCOUNT_ID);
                SellerViewOffsUI.getInstance().sort();
                rightInput = true;
            }
//            else if(input.trim().matches("(?i)sort\\s+by\\s+end\\s*date"))
//            {
//                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
//                rightInput = true;
//            }
//            else if(input.trim().matches("(?i)view\\s+discount\\s+code\\s+(.+)"))
//            {
//                ManagerViewDiscountCodeUI.getInstance().setCode(input.split("\\s+")[3]);
//                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
//                rightInput = true;
//            }
            else if(input.trim().matches("(?i)edit\\s+discount\\s+code\\s+(.+)"))
            {
                ManagerEditDiscountCodeUI.getInstance().setId(input.split("\\s+")[3]);
                goToNextPage(ManagerEditDiscountCodeUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)remove\\s+discount\\s+code\\s+(.+)"))
            {
                ManagerRemoveDiscountCodeUI.getInstance().setId(input.split("\\s+")[3]);
                goToNextPage(ManagerRemoveDiscountCodeUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerManageProductsUI.getInstance()))
        {
            if(input.trim().matches("(?i)remove\\s+(.+)"))
            {
                ManagerRemoveProductUI.getInstance().setID(input.split("\\s+")[1]);
                goToNextPage(ManagerManageProductsUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ManagerManageUsersUI.getInstance()))
        {
            if(input.trim().matches("(?i)view\\s+(\\S+)"))
            {
                goToNextPage(ManagerViewUI.getInstance());
                ManagerViewUI.getInstance().setUsername(input.split("\\s+")[1]);
                rightInput = true;
            }
            else if(input.trim().matches("(?i)delete\\s+user\\s+(\\S+)"))
            {
                goToNextPage(ManagerDeleteUserUI.getInstance());
                ManagerDeleteUserUI.getInstance().setUsername(input.split("\\s+")[2]);
                rightInput = true;
            }
            else if(input.trim().matches("(?i)sort\\s+by\\s+alphabet"))
            {
                ManagerManageUsersUI.getInstance().sort();
                rightInput = true;
            }
            else if(input.trim().matches("(?i)create\\s+manager\\s+profile"))
            {
                goToNextPage(ManagerCreateManagerUI.getInstance());
                rightInput = true;
            }
        }
        else if(currentMenu.equals(ProductsMainUI.getInstance()))
        {
            if (input.trim().matches("(?i)view\\s+categories")){
                goToNextPage(ViewCategoriesUI.getInstance());
                rightInput=true;
            }

            else if (input.trim().matches("(?i)filtering\\s+")){
                goToNextPage(FilteringUI.getInstance());
                rightInput=true;
            }

            else if (input.trim().matches("(?i)sorting\\s+")){
                goToNextPage(SortingUI.getInstance());
                rightInput=true;
            }
            else if (input.trim().matches("(?i)show\\s+products")){
                goToNextPage(ShowProductsAfterUI.getInstance());
                rightInput=true;
            }
            else if (input.trim().matches("(?i)show\\s+product\\s+(\\S+)")){
                goToNextPage(ProductMainUI.getInstance());
                ProductMainUI.getInstance().setProductID(input.split("\\s+")[2]);
                rightInput=true;
            }
        }
        else if (currentMenu.equals(FilteringUI.getInstance())){

            if (input.trim().matches("(?i)show\\s+available\\s+filters")){
                goToNextPage(AvailableFilterUI.getInstance());
                rightInput=true;
            }
            else if (input.trim().matches("(?i)filter\\s+(\\S+)\\s+(\\S+)")){
                goToNextPage(SelectedFilterUI.getInstance());
                SelectedFilterUI.getInstance().setFilterInput(input.split("\\s+")[1]);
                SelectedFilterUI.getInstance().setFilterType(input.split("\\s+")[2]);
                rightInput=true;
            }
            else if (input.trim().matches("(?i)current\\s+filters")){
                goToNextPage(CurrentFiltersUI.getInstance());
                rightInput=true;
            }
            else if(input.trim().matches("(?i)disable\\s+filter\\s+(\\S+)")){
                goToNextPage(DisableFilterUI.getInstance());
                DisableFilterUI.getInstance().setDisablingFilter(input.split("\\s+")[2]);
                rightInput=true;
            }
        }
        else if (currentMenu.equals(SortingUI.getInstance())){

            if (input.trim().matches("(?i)show\\s+available\\s+sorts")){
                goToNextPage(AvailableSortUI.getInstance());
                rightInput=true;
            }
            else if (input.trim().matches("(?i)sort\\s+(\\S+)")){
                goToNextPage(SelectedSortUI.getInstance());
                SelectedSortUI.getInstance().setSortType(input.split("\\s+")[1]);
                rightInput=true;
            }
            else if (input.trim().matches("(?i)current\\s+sorts")){
                goToNextPage(CurrentSortUI.getInstance());
                rightInput=true;
            }
            else if(input.trim().matches("(?i)disable\\s+sort")){
                goToNextPage(DisableSortUI.getInstance());
                rightInput=true;
            }
        }
        else if(currentMenu.equals(OffsUI.getInstance())){

            if (input.trim().matches("(?i)view\\s+categories")){
                goToNextPage(ViewCategoriesUI.getInstance());
                rightInput=true;
            }

            else if (input.trim().matches("(?i)filtering\\s+")){
                goToNextPage(FilteringOffsUI.getInstance());
                rightInput=true;
            }

            else if (input.trim().matches("(?i)sorting\\s+")){
                goToNextPage(SortingUI.getInstance());
                rightInput=true;
            }

            else if (input.trim().matches("(?i)show\\s+products")){
                goToNextPage(ShowProductsWithOffAfterUI.getInstance());
                rightInput=true;
            }
            else if (input.trim().matches("(?i)show\\s+product\\s+(\\S+)")){
                goToNextPage(ProductMainUI.getInstance());
                ProductMainUI.getInstance().setProductID(input.split("\\s+")[2]);
                rightInput=true;
            }
        }
        else if (currentMenu.equals(FilteringOffsUI.getInstance())){

            if (input.trim().matches("(?i)show\\s+available\\s+filters")){
                goToNextPage(AvailableFilterOffsUI.getInstance());
                rightInput=true;
            }
            else if (input.trim().matches("(?i)filter\\s+(\\S+)\\s+(\\S+)")){
                goToNextPage(SelectedFilterForOffUI.getInstance());
                SelectedFilterUI.getInstance().setFilterInput(input.split("\\s+")[1]);
                SelectedFilterUI.getInstance().setFilterType(input.split("\\s+")[2]);
                rightInput=true;
            }
            else if (input.trim().matches("(?i)current\\s+filters")){
                goToNextPage(CurrentFiltersOffsUI.getInstance());
                rightInput=true;
            }
            else if(input.trim().matches("(?i)disable\\s+filter\\s+(\\S+)")){
                goToNextPage(DisableFilterOffsUI.getInstance());
                DisableFilterUI.getInstance().setDisablingFilter(input.split("\\s+")[2]);
                rightInput=true;
            }
        }
        if(input.trim().matches("(?i)back"))
        {
            if(currentMenu.equals(UserLoginUI.getInstance()))
            {
                currentMenu = getLandingPageAfterSigninOrSignup();
            }
            else {
                if(seenPages.size()<=1)
                    errorInput("you can't get back from here!" , currentMenu);
                currentMenu = seenPages.get(seenPages.size()-2);
            }
            seenPages.remove(seenPages.size()-1);
            rightInput = true;
        }
        if(input.trim().equalsIgnoreCase("help"))
        {
            currentMenu.help();
            rightInput = true;
        }
        if(!rightInput)
        {
            errorInput("Wrong Input",currentMenu);
        }
        rightInput = false;
        this.run(currentMenu);
    }
    public void errorInput(String message,UI landing)
    {
        System.out.println(message);
        goToNextPage(landing);
    }
    public void run(UI menu)
    {
        menu.run();
        System.out.println("For commands on this page please enter Help.");
        processInput(scanner.nextLine());
    }
}
