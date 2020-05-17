package Controller;

import Model.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

public class Sort {

    // *************      Account        ***************

    public Map<String, Account> sortAccountHashMap(Map<String, Account> accountMap) {
        /* Sort statement */
        accountMap = sortAccountMapByKey(accountMap);
        return accountMap;
    }

    public Map<String, Account> sortAccountMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Account> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }

    // *************      Category        ***************

    public ArrayList<Category> sortCategoryArrayList(ArrayList<Category> categories){
        Collections.sort(categories);
        return categories;
    }


    // *************      Customer        ***************

    public ArrayList<Customer> sortCustomerArrayList(ArrayList<Customer> customers){
        Collections.sort(customers);
        return customers;
    }


    // *************      DiscountCode        ***************

    public Map<String, DiscountCode> sortDiscountCodeHashMap(Map<String, DiscountCode> discountCodeMap) {
        /* Sort statement */
        discountCodeMap = sortDiscountCodeMapByKey(discountCodeMap);
        return discountCodeMap;
    }

    public Map<String, DiscountCode> sortDiscountCodeMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, DiscountCode> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }


    // *************      Log        ***************
    // ****   SellLog     ****    BuyLog     ****

    public ArrayList<Log> sortBuyLogArrayList(ArrayList<Log> logs){
        Collections.sort(logs);
        return logs;
    }


    // *************      Manager        ***************

    public ArrayList<Manager> sortManagerArrayList(ArrayList<Manager> managers){
        Collections.sort(managers);
        return managers;
    }


    // *************      Off        ***************

    public ArrayList<Off> sortOffArrayList(ArrayList<Off> offs){
        Collections.sort(offs);
        return offs;
    }

    public Map<String, Off> sortOffHashMap(Map<String, Off> offMap) {
        /* Sort statement */
        offMap = sortOffMapByKey(offMap);
        return offMap;
    }

    public Map<String, Off> sortOffMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Off> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }


    // *************      Product        ***************

    public Map<String, Product> sortProductHashMap(Map<String, Product> productMap) {
        /* Sort statement */
        productMap = sortProductMapByKey(productMap);
        return productMap;
    }

    public Map<String, Product> sortProductMapByKey(Map map) {
        // TreeMap to store values of HashMap
        TreeMap<String, Product> sorted = new TreeMap<>();

        // Copy all data from hashMap into TreeMap
        sorted.putAll(map);

        return sorted;
    }


    // *************      Review        ***************

    public ArrayList<Review> sortReviewArrayList(ArrayList<Review> reviews){
        Collections.sort(reviews);
        return reviews;
    }


    // *************      Score        ***************

    public ArrayList<Score> sortScoreArrayList(ArrayList<Score> scores){
        Collections.sort(scores);
        return scores;
    }


    // *************      Seller        ***************

    public ArrayList<Seller> sortSellerArrayList(ArrayList<Seller> sellers){
        Collections.sort(sellers);
        return sellers;
    }


    // *************      ShoppingCart        ***************

}


