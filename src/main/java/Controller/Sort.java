package Controller;

import Model.Account;
import Model.DiscountCode;

import java.math.BigDecimal;
import java.util.*;

public class Sort {

    // Account

    public Map<String, Account> sortAllAccounts() {
        Map<String, Account> allAccounts = Account.getAllAccounts();
        /* Sort statement */
        allAccounts = sortByKey(allAccounts);
        return allAccounts;
    }

    public Map<String, Account> sortByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Account> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }


    // BuyLog

}
