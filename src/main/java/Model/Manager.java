package Model;

import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;

public class Manager extends Account {
    private static Manager manager = null;
    private static int lastNum= 1;
    protected static final String MANAGERID="1";


    private ArrayList<Manager> allManagers = new ArrayList<>();
    private ArrayList <DiscountCode> allDiscountCodes = new ArrayList<>();
    private ArrayList <Account> allUsers = new ArrayList<>();
    private ArrayList <Category> allCategories = new ArrayList<>();

    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password, double credit) {
        super(username, firstName, lastName, email, phoneNumber, password, credit);
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

    public static Manager getManager() { return manager; }

    public static Manager getManagerByID(String id , boolean... suspense){
        return (Manager) getAccountById(id,suspense);
    }

    @Override
    public void Initialize() {
        if (manager==null){
            manager=this;
            accountID= MANAGERID;
        }
        else {
            if (accountID==null){
                accountID=Methods.generateId(getClass().getSimpleName(),lastNum);
             allAccountsMap.put(accountID,this);
             lastNum++;
            }
        }
    }

    @Override
    public String Type(){
        return "Manager";
    }
}
