package Model;

import Model.Account.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    public static ArrayList<Product> allProductsList = new ArrayList<>();
    private static HashMap<String , Product> allProducts = new HashMap<>();
    private String productId;
    private ProductState productState;
    private String name;
    private Company company;
    private double price;
    private ArrayList<Seller> sellers;
    private boolean doesExist;
    private Category category;
    private String details;
    private double buyersAverageScore;
    ArrayList<Score> scoresList;
    ArrayList<Review> reviewsList;
    private Off off;


    // Initialization Block
    {
        scoresList = new ArrayList<>();
        reviewsList = new ArrayList<>();
    }

    public Product(String productId, String name, Company company, double price, Category category){
        setProductId(productId);
        setName(name);
        setProductState(productState);
        setCompany(company);
        setPrice(price);
        setCategory(category);
        allProductsList.add(this);
        allProducts.put(productId,this);
    }

    public static ArrayList<Product> getAllProductsList() {
        return allProductsList;
    }

    public String getProductId() {
        return productId;
    }

    public static void addProduct(Product product)
    {
        allProducts.put(product.getProductId(),product);
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
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

    public Double getBuyersAverageScore() {
        return calculateScore();
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

    public void setCompany(Company company) {
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

    public Double getProductFinalPriceConsideringOff() {
//        if (off != null && off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
//            off.removeOff();
//        }
//        if (off != null && off.getOffStatus().equals(OffStatus.APPROVED_OFF)) {
            return price * ((double) 1 - (off.getOffAmount() / (double) 100));
//        } else return price;
    }

    private double calculateScore() {
        double totalScore = 0;
        for (Score score : scoresList) {
            totalScore += score.getScore();
        }
        totalScore /= scoresList.size();

        return totalScore;
    }

    public String showProductDigest() {
        return
                "name: " + name +
                        "\n\tid: " + productId +
                        "\n\tdeta: " + details  +
                        "\n\tprice: " + price +
                        "\n\toff amount: " + off.getOffAmount()+
                        "\n\tcategory: " + category.getName() +
                        "\n\tseller(s): " + sellers.toString() +
                        "\n\taverage score: " + calculateScore();
    }



    private void setBuyersAverageScore() {
        this.buyersAverageScore = calculateScore();
    }
}
