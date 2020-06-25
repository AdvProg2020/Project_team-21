package Model;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Seller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Product {

    public static ArrayList<Product> allProductsList = new ArrayList<>();
    private static HashMap<String , Product> allProducts = new HashMap<>();
    public static ArrayList<Product> allProductWithOff=new ArrayList<>();
    private ArrayList<String> buyers = new ArrayList<>();
    private String company;
    private ArrayList<String> sellers = new ArrayList<>();
    private String category;
    ArrayList<String> scoresList;
    ArrayList<String> reviewsList;
    private String off;

    private String productId;
    private ProductState productState;
    private String name;
    private double price;
    private boolean doesExist;
    private String details;
    private double buyersAverageScore;
    private HashMap<String,String> specialFeatures = new HashMap<String, String>();
    private String imagePath;
    private String requestID;


    // Initialization Block
    {
        scoresList = new ArrayList<>();
        reviewsList = new ArrayList<>();
    }

    public Product(String productId, String name, Company company, double price, Category category,Seller seller,String imagePath){
        setProductId(productId);
        setName(name);
        setProductState(productState);
        if(company != null)
            setCompany(company);
        setPrice(price);
        if(category != null)
            setCategory(category);
        if(seller != null)
            addSeller(seller);
        addProductsWithOff();
        this.imagePath = imagePath;
    }
    public static void rewriteFiles(){
        for (String s : allProducts.keySet()) {
            SaveData.saveDataRunning(allProducts.get(s), s, SaveData.productFile);
        }
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setSpecialFeatures(ArrayList<String> specialFeatures){
        for (int i = 0; i < specialFeatures.size(); i++) {
            this.specialFeatures.put(getCategory().getSpecialFeatures().get(i), specialFeatures.get(i));
        }
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
        allProductsList.add(product);
        SaveData.saveData(product, product.getProductId(), SaveData.productFile);
    }

    public static void removeProduct(Product product)
    {
        allProducts.remove(product.getProductId());
        for (Seller seller : product.getSellers())
        {
            seller.getAllProducts().remove(product);
        }

        File file = new File(product.getProductId()+".json");
        if(file.delete()){
//            System.out.println("yes");
        } else {
//            System.out.println("hah");
        }
    }

    public void setOff(Off off) {
        this.off = off.getOffId();
    }

    public Off getOff() {
        return Off.getAllOffs().get(off);
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {

        return Company.getAllCompanies().get(company);
    }

    public ArrayList<Seller> getSellers() {
        ArrayList<Seller> res = new ArrayList<>();
        for (String seller : sellers) {
            res.add((Seller)Account.getAllAccounts().get(seller));
        }
        return res;
    }

    public Category getCategory() {
        Category res = null;
        for (Category allCategory : Category.getAllCategories()) {
            if(allCategory.getName().equals(category)){
                res = allCategory;
                break;
            }
        }
        return res;
    }

    public double getPrice() {
        return price;
    }

    public Double getBuyersAverageScore() {
        return calculateScore();
    }

    public ArrayList<Review> getReviewsList() {
        ArrayList<Review> res = new ArrayList<>();
        for (String s : reviewsList) {
            for (Review review : Review.getAllReviews()) {
                if(review.getReviewID().equals(s)){
                    res.add(review);
                    break;
                }
            }
        }
        return res;
    }


    public void setName(String name) {
        this.name = name;
    }

    private void setProductId(String productId) {
        this.productId = productId;
    }

    private void setProductState(ProductState productState) {
        this.productState = productState;
    }

    public void setCompany(Company company) {
        this.company = company.getName();
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean doesExist(){
        return this.doesExist;
    }

    public void setCategory(Category category) {
        this.category = category.getName();
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void addSeller(Seller seller){
        sellers.add(seller.getUsername());
    }

    public void addScore(Score score){
        scoresList.add(score.getScoreID());
    }

    public void addReview(Review review){
        reviewsList.add(review.getReviewID());
    }

    public static HashMap<String, Product> getAllProducts() {
        return allProducts;
    }

    public void addBuyer(Customer customer)
    {
        buyers.add(customer.getUsername());
    }

    public ArrayList<Customer> getBuyers() {
        ArrayList<Customer> res = new ArrayList<>();
        for (String buyer : buyers) {
            res.add((Customer) Account.getAllAccounts().get(buyer));
        }
        return res;
    }

    public Double getProductFinalPriceConsideringOff() {
//        if (off != null && off.getDiscountOrOffStat().equals(DiscountAndOffStat.EXPIRED)) {
//            off.removeOff();
//        }
//        if (off != null && off.getOffStatus().equals(OffStatus.APPROVED_OFF)) {
            return price * ((double) 1 - (getOff().getOffAmount() / (double) 100));
//        } else return price;
    }

    private double calculateScore() {
        double totalScore = 0;
        for (Score score : getScoresList()) {
            totalScore += score.getScore();
        }
        totalScore /= getScoresList().size();

        return totalScore;
    }

    public static Product getProductById (String productId) throws Exception{
        for (Product product : allProductsList){
            if (product.getProductId().equals(productId))
                return product;
        }
        throw new Exception("There is no product with this ID");
    }

    public String showSpecialFeatures(){
        StringBuilder features = new StringBuilder();
        for (String key : specialFeatures.keySet()){
            features.append("\n\t").append(key).append(": ").append(specialFeatures.get(key));
        }
        return features.toString();
    }

    public ArrayList<Score> getScoresList() {
        ArrayList<Score> res = new ArrayList<>();
        for (String s : scoresList) {
            for (Score score : Score.getAllScores()) {
                if(score.getScoreID().equals(s)){
                    res.add(score);
                    break;
                }
            }
        }
        return res;
    }

    public HashMap<String, String> getSpecialFeatures() {
        return specialFeatures;
    }

    public void addSpecialFeature(String specialFeature , String specialFeatureValue){
        if (!specialFeatures.containsKey(specialFeature)){
            specialFeatures.put(specialFeature,specialFeatureValue);
        }
    }

    public void removeOff(Off off){
        setOff(null);
    }

    public void removeSpecialFeature(String specialFeature ){
        specialFeatures.remove(specialFeature);
    }

    public String showProductDigest() {
        return
                "name: " + name +
                        "\n\tid: " + productId +
                        "\n\tdeta: " + details  +
                        "\n\tprice: " + price +
                        "\n\toff amount: " + getOff().getOffAmount()+
                        "\n\tcategory: " + category +
                        "\n\tseller(s): " + sellers.toString() +
                        "\n\taverage score: " + calculateScore();
    }

    public boolean hasOff(){
        for (Product product : allProductsList){
            if (product.getOff()!=null)
                return true;
        }
        return false;
    }

    public void addProductsWithOff(){
        for (Product product : allProductsList)
            if (product.hasOff())
                allProductWithOff.add(product);
    }

    private void setBuyersAverageScore() {
        this.buyersAverageScore = calculateScore();
    }

    public static void getObjectFromDatabase(){
        ArrayList<Object> objects = new ArrayList<>((SaveData.reloadObject(SaveData.productFile)));
        for (Object object : objects) {
            allProducts.put(((Product)object).getProductId() ,(Product) (object));
            allProductsList.add((Product)(object));
        }
    }

}
