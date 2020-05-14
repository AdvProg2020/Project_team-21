package View;

import Controller.Control;
import Controller.ControlManager;
import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;

import java.util.Scanner;

public class UserInfoUI extends UI {
    private static UserInfoUI instance;
    private Control controller = Control.getInstance();
    private Account user = controller.getUser();
    private boolean showPassword = false;
    private boolean managerUsers = false;
    private boolean viewUsername = false;
    private boolean deleteUser = false;
    private boolean createManagerProfile = false;
    private String deleteUserUsername = "";
    private String viewUsernameUser = "";
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

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
    }

    public void setManagerUsers(boolean managerUsers) {
        this.managerUsers = managerUsers;
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
        if(user == null)
        {
            ConsoleView.getInstance().setLandingPageAfterSigninOrSignup(ConsoleView.getInstance().getLastMenu());
            ConsoleView.getInstance().goToNextPage(UserInfoUI.getInstance());
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
            if(user instanceof Manager)
            {
                if(managerUsers)
                {
                    for (String s : Account.getAllAccounts().keySet())
                    {
                        System.out.println(s + "     " + Account.getAllAccounts().get(s).getType());
                    }
                    managerUsers = false;
                }
                if(viewUsername)
                {
                    try {
                        ControlManager.getInstance().viewUsername(viewUsernameUser);
                    }catch (Exception e){
                        ConsoleView.getInstance().errorInput(e.getMessage(),this);
                    }finally {
                        viewUsername = false;
                    }
                }
                if(deleteUser)
                {
                    try
                    {
                        Control.getInstance().deleteUser(deleteUserUsername);
                        System.out.println("User "+deleteUserUsername+" has been deleted succesfuly!");
                    }
                    catch (Exception e)
                    {
                        ConsoleView.getInstance().errorInput(e.getMessage(),this);
                    }
                    finally {
                        deleteUser = false;
                    }
                }
                if(createManagerProfile)
                {
                    try {
                        Scanner scanner = ConsoleView.getScanner();
                        System.out.println("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.println("Enter password: ");
                        String password = scanner.nextLine();
                        System.out.println("Re-Enter password: ");
                        String checkPassword = scanner.nextLine();
                        System.out.println("Enter first name: ");
                        String firstName = scanner.nextLine();
                        System.out.println("Enter last name: ");
                        String lastName = scanner.nextLine();
                        System.out.println("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.println("Enter phone number: ");
                        String phone = scanner.nextLine();
                        Control.getInstance().createAccount("manager",username,password,firstName,lastName,email,phone,checkPassword,"");
                    }catch (Exception e)
                    {
                        ConsoleView.getInstance().errorInput(e.getMessage(),this);
                    }
                    finally {
                        createManagerProfile = false;
                    }
                }
            }
        }
    }

    @Override
    public void help() {
        System.out.println("for editing any field just type : edit [field]\n");
        System.out.println("To show your password : show password");
        if(user instanceof Manager)
        {
            if(managerUsers)
            {
                System.out.println("to show information of a user : view [username]\n");
                System.out.println("to delete a user : delete [username]\n");
                System.out.println("To add a new manager : create manager profile\n");
                System.out.println("to sort by alphabet : sort by alphabet\n");
            }
            else{
                System.out.println("to manager users : manager users\n");
            }
//            System.out.println("to check off list waiting for you : off list\no check products list waiting for you : product list\no check seller list waiting for you : seller list\n");
//            System.out.println("to add a new discount code : create discount code\n");

            //System.out.println("to manage products : manage all products\n");
        }
    }
}
