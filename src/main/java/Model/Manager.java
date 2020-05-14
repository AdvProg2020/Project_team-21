package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager extends Account {
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
