package Model;

import Model.Account.Account;

public class Review implements Comparable<Review> {

    private Account user;
    private Product product;
    private String reviewText;
    private boolean hasBought;

    public Review(Account user, Product product, String reviewText, boolean hasBought){

        setUser(user);
        setProduct(product);
        setReviewText(reviewText);
        setHasBought(hasBought);
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

    @Override
    public int compareTo(Review o) {
        return getReviewText().compareTo(o.getReviewText());
    }
}
