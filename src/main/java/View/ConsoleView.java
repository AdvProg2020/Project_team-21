package View;

import Controller.Control;
import Model.Account.Account;
import Model.Account.Manager;
import View.ManagerProfileUIs.ManageDiscountCodes.*;
import View.ManagerProfileUIs.ManageProducts.ManagerManageProductsUI;
import View.ManagerProfileUIs.ManageProducts.ManagerRemoveProductUI;
import View.ManagerProfileUIs.ManageRequests.ManagerRequestsUI;
import View.ManagerProfileUIs.ManageUsers.ManagerCreateManagerUI;
import View.ManagerProfileUIs.ManageUsers.ManagerDeleteUserUI;
import View.ManagerProfileUIs.ManageUsers.ManagerManageUsersUI;
import View.ManagerProfileUIs.ManageUsers.ManagerViewUI;
import View.ProductsUIs.FilteringUI.*;
import View.ProductsUIs.ProductsMainUI.ProductsMainUI;
import View.ProductsUIs.ProductsMainUI.SelectedProductUI;
import View.ProductsUIs.ProductsMainUI.ShowProductsAfterUI;
import View.ProductsUIs.ProductsMainUI.ViewCategoriesUI;
import View.ProductsUIs.SortingUI.*;

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
            else if(input.trim().matches("(?i)view\\s+discount\\s+code\\s+(\\S+)"))
            {
                ManagerViewDiscountCodeUI.getInstance().setCode(input.split("\\s+")[3]);
                goToNextPage(ManagerViewDiscountCodeUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)edit\\s+discount\\s+code\\s+(\\S+)"))
            {
                ManagerEditDiscountCodeUI.getInstance().setId(input.split("\\s+")[3]);
                goToNextPage(ManagerEditDiscountCodeUI.getInstance());
                rightInput = true;
            }
            else if(input.trim().matches("(?i)remove\\s+discount\\s+code\\s+(\\S+)"))
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
        else if(currentMenu.equals(ProductsMainUI.getInstance())){

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
                //TODO
            }

            else if (input.trim().matches("(?i)show\\s+product\\s+(\\S+)")){
                goToNextPage(SelectedProductUI.getInstance());
                SelectedProductUI.getInstance().setProductId(input.split("\\s+")[2]);
                rightInput=true;
                //TODO
            }
        }
        else if (currentMenu.equals(FilteringUI.getInstance())){

            if (input.trim().matches("(?i)show\\s+available\\s+filters")){
                goToNextPage(AvailableFilterUI.getInstance());
                rightInput=true;
            }
            else if (input.trim().matches("(?i)filter\\s+(\\S+)")){
                goToNextPage(SelectedFilterUI.getInstance());
//                SelectedFilterUI.getInstance().setFilterInput();
//                SelectedFilterUI.getInstance().setFilterType();
                //TODO
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
