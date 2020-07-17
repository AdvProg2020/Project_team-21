package Client.GUIControllers;

import Server.Model.Product;

import java.util.*;

public class ProductSort {
    // *************      Product        ***************

    public static Map<String, Product> sortProductHashMap(Map<String, Product> productMap) {
        /* Sort statement */
        productMap = sortProductMapByKey(productMap);
        return productMap;
    }

    public static Map<String, Product> sortProductMapByKey(Map<String, Product> map) {
        // TreeMap to store values of HashMap

        Map<String, Product> productsMap = new HashMap<>();
        for (Product product : map.values()) {
            productsMap.put(product.getName(), product);
        }
        // Copy all data from hashMap into TreeMap
        TreeMap<String, Product> sorted = new TreeMap<>(productsMap);

        return sorted;
    }

    public static ArrayList<Product> sortProductByPrice(ArrayList<Product> products){
        Product.sortType = 1;
        Collections.sort(products);
        return products;
    }

    public static ArrayList<Product> sortProductByBuyersAverageScore(ArrayList<Product> products){
        Product.sortType = 2;
        Collections.sort(products);
        return products;
    }

}
