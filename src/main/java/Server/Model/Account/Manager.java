package Server.Model.Account;

//import Server.Model.BankPrime.BankAccount;
import Bank.BankAccount;
import Server.DatabaseHandler;
import Server.Model.SaveData;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Manager extends Account implements Comparable<Manager>, Serializable {
    private static ArrayList<Manager> allManagers = new ArrayList<>();


    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo) {
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        addNewManager(this);
        SaveData.saveData(this, getUsername(), SaveData.managerFile);
    }

    public static void rewriteFiles(){
        for (Manager manager : Manager.getAllManagers()) {
            SaveData.saveDataRunning(manager, manager.getUsername(), SaveData.managerFile);
        }
    }
    public String getType(){
        return "Manager";
    }

    public static void removeManager (Manager manager){
        allManagers.remove(manager);
        File file = new File("Database/" + manager.getUsername()+".json");
        file.delete();
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

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.managerFile)));
        for (Object object : objects) {
            allManagers.add((Manager) (object));

            getAllAccounts().put(((Account)object).getUsername() ,(Account)(object));
        }
    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Manager> managers = new ArrayList<>(DatabaseHandler.selectFromManager());
        for (Manager manager : managers) {
            allManagers.add(manager);
            getAllAccounts().put(manager.getUsername(), manager);
        }
    }
}
