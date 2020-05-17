package Controller;

import Model.Account;
import Model.Category;
import Model.Customer;
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

    // Category

    public ArrayList<Category> sortAllCategories(){
        List<Category> allCategories = new ArrayList<>(Category.getAllCategories());
        Collections.sort(allCategories.);

        allCategories.sort(allCategories.get().getName());
    }

    // Customer

    public ArrayList<Customer> sortAllCustomers(){
        ArrayList<Customer> allCustomers = Customer.getaAllCustomers();
        Collections.sort(allCustomers);
        return allCustomers;
    }



}


