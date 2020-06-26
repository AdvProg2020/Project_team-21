package Model;

import Controller.Control;
import Model.Account.Account;

import java.io.File;
import java.util.ArrayList;

public class Review implements Comparable<Review> {

    private String user;
    private String product;
    private String reviewText;
    private boolean hasBought;
    private static ArrayList<Review> allReviews = new ArrayList<>();
    private String reviewID;

    public Review(Account user, Product product, String reviewText, boolean hasBought){
        if(user != null)
            setUser(user);
        if(product != null)
            setProduct(product);
        setReviewText(reviewText);
        setHasBought(hasBought);
        allReviews.add(this);
        setReviewID(Control.getInstance().randomString(5));
        SaveData.saveData(this, getReviewText(), SaveData.reviewFile);
    }

    public static void rewriteFiles(){
        for (Review review : allReviews) {
            SaveData.saveDataRunning(review, review.getReviewText(), SaveData.reviewFile);
        }
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewID() {
        return reviewID;
    }

    private void setUser(Account user) {
        this.user = user.getUsername();
    }

    private void setProduct(Product product) {
        this.product = product.getProductId();
    }

    private void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    private void setHasBought(boolean hasBought) {
        this.hasBought = hasBought;
    }

    public Account getUser() {
        return Account.getAllAccounts().get(user);
    }

    public Product getProduct() {
        return Product.getAllProducts().get(product);
    }

    public String getReviewText() {
        return reviewText;
    }

    public boolean hasBought(){
        return hasBought;
    }

    public static ArrayList<Review> getAllReviews() {
        return allReviews;
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.reviewFile)));
        for (Object object : objects) {
            allReviews.add((Review) (object));
        }
    }

    @Override
    public int compareTo(Review o) {
        return getReviewText().compareTo(o.getReviewText());
    }
}
