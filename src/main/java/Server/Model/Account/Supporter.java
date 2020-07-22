package Server.Model.Account;

import Server.Model.SaveData;

import java.io.File;
import java.util.ArrayList;

public class Supporter extends Account{

    private static ArrayList<Supporter> allSupporters = new ArrayList<>();

    public Supporter(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo){
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        addNewSupporter(this);
        SaveData.saveData(this, getUsername(), SaveData.supportFile);
    }

    @Override
    public String getType() {
        return "Support";
    }

    public static ArrayList<Supporter> getAllSupporters() {
        return allSupporters;
    }

    public static void addNewSupporter (Supporter supporter){
        allSupporters.add(supporter);
    }

    public static void removeSupport (Supporter supporter){
        allSupporters.remove(supporter);
        File file = new File("Database/" + supporter.getUsername()+".json");
        file.delete();
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.supportFile)));
        for (Object object : objects) {
            allSupporters.add((Supporter) (object));
            getAllAccounts().put(((Account)object).getUsername() ,(Account)(object));
        }
    }
}
