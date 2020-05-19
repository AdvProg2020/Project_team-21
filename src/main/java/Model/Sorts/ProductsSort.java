package Model.Sorts;

import Model.Product;

import java.util.Comparator;

public abstract class ProductsSort implements Comparator {

    public static class productSortByNameAscending implements Comparator<Product>{
        public int compare(Product firstOne , Product secondOne){
            return firstOne.getName().compareToIgnoreCase(secondOne.getName());
        }
    }

    public static class productSortByNameDescending implements Comparator<Product> {
        public int compare(Product firstOne, Product secondOne) {
            return (-1) * (firstOne.getName().compareToIgnoreCase(secondOne.getName()));
        }
    }


    public static class productSortByRate implements Comparator<Product> {
        public int compare(Product firstOne, Product secondOne) {
            return (-1) * (firstOne.getBuyersAverageScore().compareTo(secondOne.getBuyersAverageScore()));
        }
    }

    public static class productSortByPriceAscending implements Comparator<Product> {
        public int compare(Product firstOne, Product secondOne) {
            return (firstOne.getProductFinalPriceConsideringOff().compareTo(secondOne.getProductFinalPriceConsideringOff()));
        }
    }

    public static class productSortByPriceDescendingly implements Comparator<Product> {
        public int compare(Product firstOne, Product secondOne) {
            return (-1) * (firstOne.getProductFinalPriceConsideringOff().compareTo(secondOne.getProductFinalPriceConsideringOff()));
        }
    }
}
