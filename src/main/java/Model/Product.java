package Model;

import View.ProductsUI;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    //private static ArrayList<Product> allProducts = new ArrayList<>();
    private static HashMap<String , Product> allProducts = new HashMap<>();
    private String productId;
    private ProductState productState;
    private String name;
    private String company;
    private double price;
    private ArrayList<Seller> sellers;
    private boolean doesExist;
    private Category category;
    private String details;
    private double buyersAverageScore;
    ArrayList<Score> scoresList;
    ArrayList<Review> reviewsList;


    // Initialization Block
    {
        scoresList = new ArrayList<>();
        reviewsList = new ArrayList<>();
    }

    public Product(String productId, String name, ProductState productState, String company, double price){

        setProductId(productId);
        setName(name);
        setProductState(productState);
        setCompany(company);
        setPrice(price);
        allProducts.put(productId,this);
    }

    public String getProductId() {
        return productId;
    }


    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getBuyersAverageScore() {
        return buyersAverageScore;
    }

    public ArrayList<Review> getReviewsList() {
        return reviewsList;
    }


    private void setName(String name) {
        this.name = name;
    }

    private void setProductId(String productId) {
        this.productId = productId;
    }

    private void setProductState(ProductState productState) {
        this.productState = productState;
    }

    private void setCompany(String company) {
        this.company = company;
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public boolean doesExist(){
        return this.doesExist;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void addSeller(Seller seller){
        sellers.add(seller);
    }

    public void addScore(Score score){
        scoresList.add(score);
    }

    public void addReview(Review review){
        reviewsList.add(review);
    }

    public static HashMap<String, Product> getAllProducts() {
        return allProducts;
    }

    private double calculateScore() {
        double totalScore = 0;
        for (Score score : scoresList) {
            totalScore += score.getScore();
        }
        totalScore /= scoresList.size();

        return totalScore;
    }

    private void setBuyersAverageScore() {
        this.buyersAverageScore = calculateScore();
    }
}
