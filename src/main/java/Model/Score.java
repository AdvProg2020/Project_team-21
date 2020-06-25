package Model;

import Controller.Control;
import Model.Account.Account;

import java.io.File;
import java.util.ArrayList;

public class Score implements Comparable<Score>{

    private String user;
    private String product;

    private int score;
    private static ArrayList<Score> allScores = new ArrayList<>();
    private String scoreID;

    public Score(Account user, Product product, int score)
    {
        if(user != null)
            setUser(user);
        if(product != null)
            setProduct(product);
        setScore(score);
        allScores.add(this);
        setScoreID(Control.getInstance().randomString(5));
        SaveData.saveData(this, (getScore()+getUser().getFirstName()), SaveData.scoreFile);
    }
    public static void rewriteFiles(){
        for (Score score : allScores) {
            SaveData.saveDataRunning(score, score.getScore() + score.getUser().getFirstName(), SaveData.scoreFile);
        }
    }

    public void setScoreID(String scoreID) {
        this.scoreID = scoreID;
    }

    public String getScoreID() {
        return scoreID;
    }

    public static ArrayList<Score> getAllScores() {
        return allScores;
    }

    public void setUser(Account user) {
        this.user = user.getUsername();
    }

    public void setProduct(Product product) {
        this.product = product.getProductId();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Account getUser() {

        return Account.getAllAccounts().get(user);
    }

    public Product getProduct() {
        return Product.getAllProducts().get(product);
    }

    public int getScore() {
        return score;
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.scoreFile)));
        for (Object object : objects) {
            allScores.add((Score) (object));
        }
    }

    @Override
    public int compareTo(Score o) {
        return (getScore() - o.getScore());
    }
}
