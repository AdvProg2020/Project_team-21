package Model.Sorts;

import Model.Product;

import java.util.Comparator;

public abstract class ProductsSort implements Comparator {

    public static class productSortByNameAscending implements Comparator<Product>{
        public int compare(Product one , Product two){
            return one.getName().compareToIgnoreCase(two.getName());
        }
    }

    public static class productSortByNameDescending implements Comparator<Product> {
        public int compare(Product one, Product two) {
            return (-1) * (one.getName().compareToIgnoreCase(two.getName()));
        }
    }


    public static class productSortByRate implements Comparator<Product> {
        public int compare(Product one, Product two) {
            return (-1) * (one.getBuyersAverageScore().compareTo(two.getBuyersAverageScore()));
        }
    }

    public static class productSortByPriceAscending implements Comparator<Product> {
        public int compare(Product one, Product two) {
            return (one.getProductFinalPriceConsideringOff().compareTo(two.getProductFinalPriceConsideringOff()));
        }
    }

    public static class productSortByPriceDescendingly implements Comparator<Product> {
        public int compare(Product one, Product two) {
            return (-1) * (one.getProductFinalPriceConsideringOff().compareTo(two.getProductFinalPriceConsideringOff()));
        }
    }
}
