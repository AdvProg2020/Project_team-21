package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager extends Account implements Comparable<Manager>{
    private static ArrayList<Manager> allManagers = new ArrayList<>();

    private static ArrayList<Seller> sellerActivationList = new ArrayList<>();
    private static HashMap<RequestType,Product> productChangeList = new HashMap<>();
    private static HashMap<RequestType,Off> offChangeList = new HashMap<>();

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

    @Override
    public int compareTo(Manager o) {
        return getUsername().compareTo(o.getUsername());
    }
}
