package Model.Account;

import Model.Off;
import Model.Product;
import Model.Request.RequestType;
import Model.SaveData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager extends Account implements Comparable<Manager>{
    private static ArrayList<Manager> allManagers = new ArrayList<>();


    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo) {
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        addNewManager(this);
        SaveData.saveData(this, getUsername(), SaveData.managerFile);
    }
    public static void rewriteFiles(){
        for (Manager manager : Manager.getAllManagers()) {
            File file = new File(manager.getUsername()+".json");
            file.delete();
            SaveData.saveData(manager, manager.getUsername(), SaveData.managerFile);
        }
    }

    public String getType(){
        return "Manager";
    }

    public static void removeManager (Manager manager){
        allManagers.remove(manager);

        File file = new File(manager.getUsername()+".json");
        if(file.delete()){
//            System.out.println("yes");
        } else {
//            System.out.println("hah");
        }
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
}
