package Server.Model.Account;

import Server.Model.SaveData;

import java.io.File;
import java.util.ArrayList;

public class Manager extends Account implements Comparable<Manager>{
    private static ArrayList<Manager> allManagers = new ArrayList<>();

    private int wage = 5;

    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo) {
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        addNewManager(this);
        SaveData.saveData(this, getUsername(), SaveData.managerFile);
    }
    //    public static void rewriteFiles(){
//        for (Manager manager : Manager.getAllManagers()) {
//            File file = new File(manager.getUsername()+".json");
//            file.delete();
//            SaveData.saveData(manager, manager.getUsername(), SaveData.managerFile);
//        }
//    }
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

        File file = new File(manager.getUsername()+".json");
        if(file.delete()){
//            System.out.println("yes");
        } else {
//            System.out.println("hah");
        }
    }

    public int getWage() {
        return wage;
    }

    public void setWage(int wage) {
        this.wage = wage;
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
