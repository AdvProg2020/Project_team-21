package View;

import Controller.Control;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;

import java.util.Scanner;
import java.util.ServiceConfigurationError;

public class UserInfoUI extends UI {
    private static UserInfoUI instance;
    private Control controller = Control.getInstance();
    private Account user = controller.getUser();;
    private boolean showPassword = false;
    private boolean managerUsers = false;
    private boolean viewUsername = false;
    private boolean deleteUser = false;
    private boolean createManagerProfile = false;
    private boolean manageAllProducts = false;
    private String deleteUserUsername = "";
    private String viewUsernameUser = "";
    private boolean editField = false;
    private String editFieldField = "";
    private boolean newToInfo = true;
    private UserInfoUI()
    {

    }
    public static UserInfoUI getInstance() {
        if(instance == null)
            instance = new UserInfoUI();
        return instance;
    }

    public void setCreateManagerProfile(boolean createManagerProfile) {
        this.createManagerProfile = createManagerProfile;
    }

    public void setDeleteUserUsername(String deleteUserUsername) {
        this.deleteUserUsername = deleteUserUsername;
    }

    public void setEditField(boolean editField) {
        this.editField = editField;
    }

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
        this.newToInfo  = true;
    }

    public void setManagerUsers(boolean managerUsers) {
        this.managerUsers = managerUsers;
    }

    public void setEditFieldField(String editFieldField) {
        this.editFieldField = editFieldField;
    }

    public void setViewUsername(boolean viewUsername) {
        this.viewUsername = viewUsername;
    }

    public void setViewUsernameUser(String viewUsernameUser) {
        this.viewUsernameUser = viewUsernameUser;
    }

    public String getViewUsernameUser() {
        return viewUsernameUser;
    }

    public void setDeleteUser(boolean deleteUser) {
        this.deleteUser = deleteUser;
    }

    public void setNewToInfo(boolean newToInfo) {
        this.newToInfo = newToInfo;
    }


    @Override
    public void run() {
        user = Control.getInstance().getUser();
        if(user == null)
        {
            if(ConsoleView.getInstance().getLastMenu() == null)
                ConsoleView.getInstance().setLandingPageAfterSigninOrSignup(MainMenuUI.getInstance());
            else
                ConsoleView.getInstance().setLandingPageAfterSigninOrSignup(ConsoleView.getInstance().getLastMenu());
            ConsoleView.getInstance().goToNextPage(UserLoginUI.getInstance());
            UserLoginUI.getInstance().run();
        }
        else
        {
            if(newToInfo)
            {
                System.out.println("First Name: " + user.getFirstName());
                System.out.println("Last Name: " + user.getLastName());
                System.out.println("Username: " + user.getUsername());
                if(showPassword)
                {
                    System.out.println("Password: " + user.getPassword());
                    showPassword = false;
                }
                else
                    System.out.println("Password: " + "********");
                System.out.println("Email: " + user.getEmail());
                System.out.println("Phone Number: " + user.getPhoneNumber());
                newToInfo = false;
            }
            else if(editField)
            {
                Scanner scanner = ConsoleView.getScanner();
                if(editFieldField.matches("first\\s*name"))
                {
                    System.out.println("Enter your new first name: ");
                    user.setFirstName(scanner.nextLine());
                    System.out.println("Your first name has been edited successfully");
                }
                else if(editFieldField.matches("last\\s*name"))
                {
                    System.out.println("Enter your new last name: ");
                    user.setLastName(scanner.nextLine());
                    System.out.println("Your last name has been edited successfully");
                }
                else if(editFieldField.matches("email"))
                {
                    System.out.println("Enter your new email: ");
                    user.setEmail(scanner.nextLine());
                    System.out.println("Your email has been edited successfully");
                }
                else if(editFieldField.matches("phone\\s*number|phone|number"))
                {
                    System.out.println("Enter your new phone number: ");
                    user.setPhoneNumber(scanner.nextLine());
                    System.out.println("Your phone number has been edited successfully");
                }
                else if(editFieldField.matches("password|pass"))
                {
                    System.out.println("Enter your new password: ");
                    user.setPassword(scanner.nextLine());
                    System.out.println("Your password has been edited successfully");
                }
                else
                {
                    ConsoleView.getInstance().errorInput("This field wasn't recognized!" , this);
                }
                editField = false;
            }
        }
    }

    @Override
    public void help() {
        System.out.println("for editing any field just type : edit [field]");
        System.out.println("To show your password : show password");
        if(user instanceof Manager)
        {
            System.out.println("To manager and see users : manage users");
            System.out.println("To manage and see products : manage all products");
            System.out.println("To manage and see discount codes : view discount codes");
            System.out.println("To manage and see requests : manage requests");
            System.out.println("To manage and see categories : manage categories");
            System.out.println("To add money to someone : add balance");
        }
        else if(user instanceof Seller)
        {
            System.out.println("To view user's company information : view company information");
            System.out.println("To see history of sales : view sales history");
            System.out.println("To see and manage products : manage products");
            System.out.println("To send a request for adding a product : add product");
            System.out.println("To send a request for removing a product : remove product [productID]");
            System.out.println("To see all categories : Show categories");
            System.out.println("To see and manage your offs : view offs");
            System.out.println("To view your balance : view balance");
        }
        else if(user instanceof Customer)
        {
            System.out.println("To view your cart : view cart");
            System.out.println("To view your orders : view orders");
            System.out.println("To view your balance : view balance");
            System.out.println("To view your discount codes : view discount codes");
        }
    }
}
