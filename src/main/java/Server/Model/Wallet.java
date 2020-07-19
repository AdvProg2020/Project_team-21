package Server.Model;

import Server.Model.Account.Account;

public class Wallet {
    private static int leastAmount= 0;
    private final Account account;
    private int amount;

    public Wallet(Account account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public static int getLeastAmount() {
        return leastAmount;
    }

    public static void setLeastAmount(int leastAmount) {
        Wallet.leastAmount = leastAmount;
    }

    public Account getAccount() {
        return account;
    }

    public int getAmount() {
        return amount;
    }

    public void increaseAmount (int amount){
        this.amount += amount;
    }

    public void decreaseAmount (int amount) throws Exception{
        if (this.amount - amount < leastAmount)
            throw new Exception("Can't decrease this amount !");
        this.amount-=amount;
    }
}
