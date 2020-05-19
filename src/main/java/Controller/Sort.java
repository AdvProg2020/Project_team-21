package Controller;

import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Log.Log;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

public class Sort {

    // *************      Account        ***************

    public static Map<String, Account> sortAccountHashMap(Map<String, Account> accountMap) {
        /* Sort statement */
        accountMap = sortAccountMapByKey(accountMap);
        return accountMap;
    }

    public static Map<String, Account> sortAccountMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Account> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }

    // *************      Category        ***************

    public static ArrayList<Category> sortCategoryArrayList(ArrayList<Category> categories){
        Collections.sort(categories);
        return categories;
    }


    // *************      Customer        ***************

    public static ArrayList<Customer> sortCustomerArrayList(ArrayList<Customer> customers){
        Collections.sort(customers);
        return customers;
    }


    // *************      DiscountCode        ***************

    public static Map<String, DiscountCode> sortDiscountCodeHashMap(Map<String, DiscountCode> discountCodeMap) {
        /* Sort statement */
        discountCodeMap = sortDiscountCodeMapByKey(discountCodeMap);
        return discountCodeMap;
    }

    public static Map<String, DiscountCode> sortDiscountCodeMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, DiscountCode> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }


    public static ArrayList<DiscountCode> sortDiscountCodesByDiscountPercentage(ArrayList<DiscountCode> discountCodes){
        discountCodes.sort(Comparator.comparingDouble(DiscountCode::getDiscountPercentage));
        return discountCodes;
    }

    // *************      Log        ***************
    // ****   SellLog     ****    BuyLog     ****

    public static ArrayList<Log> sortBuyLogArrayList(ArrayList<Log> logs){
        Collections.sort(logs);
        return logs;
    }


    // *************      Manager        ***************

    public static ArrayList<Manager> sortManagerArrayList(ArrayList<Manager> managers){
        Collections.sort(managers);
        return managers;
    }


    // *************      Off        ***************

    public static ArrayList<Off> sortOffArrayListByAmount(ArrayList<Off> offs){
        Collections.sort(offs);
        return offs;
    }

    public static Map<String, Off> sortOffHashMap(Map<String, Off> offMap) {
        /* Sort statement */
        offMap = sortOffMapByKey(offMap);
        return offMap;
    }

    public static Map<String, Off> sortOffMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Off> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }


    // *************      Product        ***************

    public static Map<String, Product> sortProductHashMap(Map<String, Product> productMap) {
        /* Sort statement */
        productMap = sortProductMapByKey(productMap);
        return productMap;
    }

    public static Map<String, Product> sortProductMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Product> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }


    // *************      Review        ***************

    public static ArrayList<Review> sortReviewArrayList(ArrayList<Review> reviews){
        Collections.sort(reviews);
        return reviews;
    }


    // *************      Score        ***************

    public static ArrayList<Score> sortScoreArrayList(ArrayList<Score> scores){
        Collections.sort(scores);
        return scores;
    }


    // *************      Seller        ***************

    public static ArrayList<Seller> sortSellerArrayList(ArrayList<Seller> sellers){
        Collections.sort(sellers);
        return sellers;
    }


    // *************      ShoppingCart        ***************

}


