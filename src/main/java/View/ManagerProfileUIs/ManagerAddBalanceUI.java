package View.ManagerProfileUIs;

import Model.Account.Account;
import Model.Account.Customer;
import View.ConsoleView;
import View.UI;

import java.util.Scanner;

public class ManagerAddBalanceUI extends UI {
    private static ManagerAddBalanceUI instance;
    private ManagerAddBalanceUI()
    {

    }

    public static ManagerAddBalanceUI getInstance()
    {
        if(instance == null)
            instance = new ManagerAddBalanceUI();
        return instance;
    }
    @Override
    public void run()
    {
        Scanner scanner = ConsoleView.getScanner();
        System.out.println("Enter username you want to add money to: ");
        String userName = scanner.nextLine();
        if(!Account.getAllAccounts().containsKey(userName))
        {
            ConsoleView.getInstance().errorInput("This username does not exist.",ConsoleView.getInstance().getLastMenu());
        }
        else
        {
            System.out.println("Enter amount you want to add: ");
            String money = scanner.nextLine();
            if(money.matches("\\d+.?\\d*"))
            {
                Account user = Account.getAllAccounts().get(userName);
                if(user instanceof Customer)
                {
                    Customer customer = (Customer) user;
                    customer.setBalance(customer.getBalance()+Double.parseDouble(money));
                }
                else
                {
                    user.setCredit(user.getCredit() + Double.parseDouble(money));
                }
                ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
            }
            else
            {
                ConsoleView.getInstance().errorInput("Money format is wrong.",ConsoleView.getInstance().getLastMenu());
            }
        }
    }

    @Override
    public void help() {

    }

}
