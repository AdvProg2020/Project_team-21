package Model;

import java.util.ArrayList;

public class Manager extends Account {
    private static ArrayList<Manager> allManagers = new ArrayList<>();

    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        addNewManager(this);
    }

    public String getType(){
        return "Manager";
    }


    public void removeManager (Account account){
        allManagers.remove(account);
    }

    public static ArrayList<Manager> getAllManagers() {
        return allManagers;
    }

    public void addNewManager (Manager manager){
        allManagers.add(manager);
    }
}
