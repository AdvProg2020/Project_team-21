package Model.Account;

import Model.Off;
import Model.Product;
import Model.Request.RequestType;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager extends Account {
    private static ArrayList<Manager> allManagers = new ArrayList<>();


    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
        addNewManager(this);
    }

    public String getType(){
        return "Manager";
    }

    public static void removeManager (Manager manager){
        allManagers.remove(manager);
    }

    public static ArrayList<Manager> getAllManagers() {
        return allManagers;
    }

    public static void addNewManager (Manager manager){
        allManagers.add(manager);
    }

}