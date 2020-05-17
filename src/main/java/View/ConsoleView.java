package View;

import Controller.Control;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import View.CustomerProfileUIs.CustomerViewCartUI;
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
import View.SellerProfileUIs.*;
import View.SellerProfileUIs.ManageOffs.SellerAddOffUI;
import View.SellerProfileUIs.ManageOffs.SellerEditOffUI;
import View.SellerProfileUIs.ManageOffs.SellerViewOffInfoUI;
import View.SellerProfileUIs.ManageOffs.SellerViewOffsUI;
import View.SellerProfileUIs.ManageProducts.SellerEditProductUI;
import View.SellerProfileUIs.ManageProducts.SellerManageProductsUI;
import View.SellerProfileUIs.ManageProducts.SellerViewProductBuyersUI;
import View.SellerProfileUIs.ManageProducts.SellerViewProductUI;

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
            }
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
            }
        }
        else if(currentMenu.equals(CustomerViewCartUI.getInstance()))
        {

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
                rightInput = true;
            }
            else if(input.trim().matches("(?i)sort\\s+by\\s+start\\s*date"))
            {
                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)sort\\s+by\\s+end\\s*date"))
            {
                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)view\\s+discount\\s+code\\s+(.+)"))
            {
                ManagerViewDiscountCodeUI.getInstance().setCode(input.split("\\s+")[3]);
                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
                rightInput = true;
            }
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
