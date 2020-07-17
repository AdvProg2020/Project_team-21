package Server.Model.Sorts;

import Server.Model.Account.Account;

import java.util.Comparator;

public abstract class UserSort  implements Comparator{
    public static class userSortByFirstNameAscending implements Comparator<Account>{
        public int compare (Account firstOne , Account secondOne){
            return firstOne.getFirstName().compareToIgnoreCase(secondOne.getFirstName());
        }
    }

    public static class usersSortByLastNameAscending implements Comparator<Account> {
        public int compare(Account firstOne, Account secondOne) {
            return firstOne.getLastName().compareToIgnoreCase(secondOne.getLastName());
        }
    }

    public static class usersSortByFirstNameDescending implements Comparator<Account> {
        public int compare(Account firstOne, Account secondOne) {
            return (-1) * (firstOne.getFirstName().compareToIgnoreCase(secondOne.getFirstName()));
        }
    }

    public static class usersSortByLastNameDescending implements Comparator<Account> {
        public int compare(Account firstOne, Account secondOne) {
            return (-1) * (firstOne.getLastName().compareToIgnoreCase(secondOne.getLastName()));
        }
    }

    public static class productSortByType implements Comparator<Account> {
        public int compare(Account firstOne, Account secondONe) {
            if (firstOne.getType().compareToIgnoreCase(secondONe.getType()) == 0)
                return firstOne.getFirstName().compareToIgnoreCase(secondONe.getFirstName());
            else
                return firstOne.getType().compareToIgnoreCase(secondONe.getType());
        }
    }
}
