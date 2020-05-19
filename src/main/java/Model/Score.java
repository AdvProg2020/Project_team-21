package Model;

import Model.Account.Account;

public class Score implements Comparable<Score>{

    private Account user;
    private Product product;
    private int score;

    public Score(Account user, Product product, int score)
    {
        setUser(user);
        setProduct(product);
        setScore(score);
        SaveData.saveData(this, (getScore()+getUser().getFirstName()), SaveData.scoreFile);
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Account getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Score o) {
        return (getScore() - o.getScore());
    }
}
