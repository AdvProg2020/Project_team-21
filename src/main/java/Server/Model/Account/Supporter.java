package Server.Model.Account;

import java.util.ArrayList;

public class Supporter extends Account{

    private static ArrayList<Supporter> allSupporters;

    // Initialization Block
    {
        allSupporters = new ArrayList<>();
    }
    public Supporter(String username, String firstName, String lastName, String email, String phoneNumber, String password,String photo){
        super(username, firstName, lastName, email, phoneNumber, password,photo);
        addNewSupporter(this);
    }

    @Override
    public String getType() {
        return "Supporter";
    }

    public ArrayList<Supporter> getAllSupporters() {
        return allSupporters;
    }

    public static void addNewSupporter (Supporter supporter){
        allSupporters.add(supporter);
    }
}
