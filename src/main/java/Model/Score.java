package Model;

import Model.Account.Account;

import java.io.File;
import java.util.ArrayList;

public class Score implements Comparable<Score>{

    private Account user;
    private Product product;
    private int score;
    private static ArrayList<Score> allScores = new ArrayList<>();

    public Score(Account user, Product product, int score)
    {
        setUser(user);
        setProduct(product);
        setScore(score);
        allScores.add(this);
        SaveData.saveData(this, (getScore()+getUser().getFirstName()), SaveData.scoreFile);
    }
    public static void rewriteFiles(){
        for (Score score : allScores) {
            File file = new File(score.getScore() + score.getUser().getFirstName()+".json");
            file.delete();
            SaveData.saveData(score, score.getScore() + score.getUser().getFirstName(), SaveData.scoreFile);
        }
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
