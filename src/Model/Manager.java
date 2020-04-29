package Model;

import java.util.ArrayList;

public class Manager extends Account {
    private ArrayList<Manager> allManagers = new ArrayList<>();
    private ArrayList <DiscountCode> allDiscountCodes = new ArrayList<>();
    private ArrayList <Account> allUsers = new ArrayList<>();
    private ArrayList <Category> allCategories = new ArrayList<>();

    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password, ArrayList<DiscountCode> discountList, double credit) {
        super(username, firstName, lastName, email, phoneNumber, password, discountList, credit);
    }

    public String getType(){
        return "Manager";
    }

    public void addAccount (Account account){
        allUsers.add(account);
    }

    public void removeAccount (Account account){
        allUsers.remove(account);
    }

    public void addNewManager (Manager manager){
        allManagers.add(manager);
    }
}
