package Model;

import Model.Account.Account;

import java.io.File;
import java.util.ArrayList;

public class Review implements Comparable<Review> {

    private Account user;
    private Product product;
    private String reviewText;
    private boolean hasBought;
    private static ArrayList<Review> allReviews = new ArrayList<>();

    public Review(Account user, Product product, String reviewText, boolean hasBought){
        setUser(user);
        setProduct(product);
        setReviewText(reviewText);
        setHasBought(hasBought);
        allReviews.add(this);
        SaveData.saveData(this, getReviewText(), SaveData.reviewFile);
    }

    public static void rewriteFiles(){
        for (Review review : allReviews) {
            File file = new File(review.getReviewText()+".txt");
            file.delete();
            SaveData.saveData(review, review.getReviewText(), SaveData.reviewFile);
        }
    }


    private void setUser(Account user) {
        this.user = user;
    }

    private void setProduct(Product product) {
        this.product = product;
    }

    private void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    private void setHasBought(boolean hasBought) {
        this.hasBought = hasBought;
    }

    public Account getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
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
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.categoryFile)));
        for (Object object : objects) {
            allReviews.add((Review) (object));
        }
    }

    @Override
    public int compareTo(Review o) {
        return getReviewText().compareTo(o.getReviewText());
    }
}
