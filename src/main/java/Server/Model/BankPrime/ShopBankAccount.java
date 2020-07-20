package Server.Model.BankPrime;

public class ShopBankAccount {
    private static ShopBankAccount shopBankAccount = new ShopBankAccount();

    public static ShopBankAccount getInstance(){
        return shopBankAccount;
    }

    private ShopBankAccount (){
        new BankAccount("Shop" , "Account" ,"Shop" , "123");
    }

    //TODO: toye main az in bayad ye instance gerefte beshe
}
