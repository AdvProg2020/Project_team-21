package Client.Model;

import javafx.scene.image.Image;

public class Product implements Comparable<Product>{
    private String name;
    private double price;
    private double orgPrice;
    private double buyersAverageScore;
    private String companyName;
    private String companyAddress;
    private String categoryName;
    private Image image;
    private String productId;

    public Product(String name, double price, double orgPrice, double buyersAverageScore, String companyName, String companyAddress, String categoryName,Image image,String productId) {
        this.name = name;
        this.price = price;
        this.orgPrice = orgPrice;
        this.buyersAverageScore = buyersAverageScore;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.categoryName = categoryName;
        this.image = image;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getOrgPrice() {
        return orgPrice;
    }

    public double getBuyersAverageScore() {
        return buyersAverageScore;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Image getImage() {
        return image;
    }

    public String getProductId() {
        return productId;
    }

    public static int sortType = 0; //  1 --> Price // 2 --> buyers average score

    @Override
    public int compareTo(Product o) {
        if(sortType==1){
            return (int)(getPrice()-(o.getPrice()));
        } else {
            return (int)((o.getBuyersAverageScore()) - getBuyersAverageScore());
        }
    }
}
