package Server;

import Bank.BankAccount;
import Server.Model.*;
import Server.Model.Account.Customer;
import Server.Model.Account.Manager;
import Server.Model.Account.Seller;
import Server.Model.Account.Supporter;
import Server.Model.Log.BuyLog;
import Server.Model.Log.SellLog;
import Server.Model.Request.OffRequest;
import Server.Model.Request.ProductRequest;
import Server.Model.Request.Request;
import Server.Model.Request.SellerRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    private static Connection c = null;
    private static Statement stmt = null;

    public DatabaseHandler(){
        // Try connect to database
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:MarketDatabase.db");
            System.out.println("Connected to database successfully!");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void executeQuery(String query){
        try{
            stmt = c.createStatement();
            stmt.executeQuery(query);
        } catch (Exception e){
            // error
        }

    }

    public void closeConnection(){
        try {
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


//    public static void saveAuction(String auctionId, String auctionProductId, String startTime, String endTime, String maxSuggestedAmount, String sellerUsername, String auctionWinnerUsername, String customersSuggestionAmount) throws IOException {
//        String query = String.format("INSERT INTO Auction (auctionId, auctionProductId, startTime, endTime, maxSuggestedAmount," +
//                " sellerUsername, auctionWinnerUsername, customersSuggestionAmount) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", serialize(auctionId),
//                serialize(auctionProductId), serialize(startTime), serialize(endTime), serialize(maxSuggestedAmount), serialize(sellerUsername), serialize(auctionWinnerUsername), serialize(customersSuggestionAmount));
//        executeQuery(query);
//    }
//
//    public static ArrayList<Auction> retrieveAuctions(){
//        ArrayList<Auction> auctions = new ArrayList<>();
//
//        try {
//            stmt = c.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM Auction;");
//            while(rs.next()){
//
//                Object auctionId = deserialize(rs.getString("auctionId"));
//                Object auctionProductId = deserialize(rs.getString("auctionProductId"));
//                Object startTime = deserialize(rs.getString("startTime"));
//                Object endTime = deserialize(rs.getString("endTime"));
//                Object maxSuggestedAmount = deserialize(rs.getString("maxSuggestedAmount"));
//                Object sellerUsername = deserialize(rs.getString("sellerUsername"));
//                Object auctionWinnerUsername = deserialize(rs.getString("auctionWinnerUsername"));
//                Object customersSuggestionAmount = deserialize(rs.getString("customersSuggestionAmount"));
//
//                Auction auction = new Auction((String)auctionId, Product.getProductById((String) auctionProductId), (LocalDateTime) startTime, (LocalDateTime) endTime, Seller.getSellerWithUsername((String) sellerUsername));
//                auction.setMaxSuggestedAmount((Double) maxSuggestedAmount);
//                auction.setAuctionWinner((String)auctionWinnerUsername);
//
//                auction.setCustomersSuggestionAmount((HashMap<String, Double>) customersSuggestionAmount);
//                auctions.add(auction);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return auctions;
//    }
//
//    private static String serialize(Serializable o) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        oos.writeObject(o);
//        oos.close();
//        return Base64.getEncoder().encodeToString(baos.toByteArray());
//    }
//
//    private static Object deserialize(String s) throws IOException,
//            ClassNotFoundException {
//        byte[] data = Base64.getDecoder().decode(s);
//        ObjectInputStream ois = new ObjectInputStream(
//                new ByteArrayInputStream(data));
//        Object o = ois.readObject();
//        ois.close();
//        return o;
//    }



    public static void insertIntoDatabase(Object o, DataTypeEnum type) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            // serialize the employee object into a byte array
            byte[] employeeAsBytes = baos.toByteArray();

            PreparedStatement pstmt = null;
            if(type== DataTypeEnum.AUCTION){
                pstmt = c.prepareStatement("INSERT INTO Auction (auctionSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.BUYLOG){
                pstmt = c.prepareStatement("INSERT INTO BuyLog (buyLogSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.CATEGORY){
                pstmt = c.prepareStatement("INSERT INTO Category (categorySerialized) VALUES(?)");
            } else if(type== DataTypeEnum.COMPANY){
                pstmt = c.prepareStatement("INSERT INTO Company (companySerialized) VALUES(?)");
            } else if(type== DataTypeEnum.CUSTOMER){
                pstmt = c.prepareStatement("INSERT INTO Customer (customerSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.DISCOUNTCODE){
                pstmt = c.prepareStatement("INSERT INTO DiscountCode (discountCodeSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.MANAGER){
                pstmt = c.prepareStatement("INSERT INTO Manager (managerSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.OFF){
                pstmt = c.prepareStatement("INSERT INTO Off (offSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.OFFREQUEST){
                pstmt = c.prepareStatement("INSERT INTO OffRequest (offRequestSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.PRODUCT){
                pstmt = c.prepareStatement("INSERT INTO Product (productSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.PRODUCTREQUEST){
                pstmt = c.prepareStatement("INSERT INTO ProductRequest (productRequestSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.REVIEW){
                pstmt = c.prepareStatement("INSERT INTO Review (reviewSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.SELLLOG){
                pstmt = c.prepareStatement("INSERT INTO SellLog (sellLogSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.SELLER){
                pstmt = c.prepareStatement("INSERT INTO Seller (sellerSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.SELLERREQUEST){
                pstmt = c.prepareStatement("INSERT INTO SellRequest (sellRequestSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.SHOPPINGCART){
                pstmt = c.prepareStatement("INSERT INTO ShoppingCart (shoppingCartSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.SUPPORTER){
                pstmt = c.prepareStatement("INSERT INTO Supporter (supporterSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.WALLET){
                pstmt = c.prepareStatement("INSERT INTO Wallet (walletSerialized) VALUES(?)");
            } else if(type== DataTypeEnum.BANKACCOUNT){
                pstmt = c.prepareStatement("INSERT INTO BankAccount (bankAccountSerialized) VALUES(?)");
            }

            else if(type== DataTypeEnum.OFFATREQUEST) {
                pstmt = c.prepareStatement("INSERT INTO OffAtRequest (serialized) VALUES(?)");
            } else if(type== DataTypeEnum.PRODUCTATREQUEST){
                pstmt = c.prepareStatement("INSERT INTO ProductAtRequest (serialized) VALUES(?)");
            } else if(type== DataTypeEnum.SELLERATREQUEST){
                pstmt = c.prepareStatement("INSERT INTO SellerAtRequest (serialized) VALUES(?)");
            }

            ByteArrayInputStream bais =
                    new ByteArrayInputStream(employeeAsBytes);
            // bind our byte array  to the emp column
            pstmt.setBinaryStream(1,bais, employeeAsBytes.length);
            pstmt.executeUpdate();
//            theConn.c.commit();
            pstmt.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteDatabases(){
        DatabaseHandler.executeQuery("DELETE FROM Auction");
        DatabaseHandler.executeQuery("DELETE FROM BuyLog");
        DatabaseHandler.executeQuery("DELETE FROM Category");
        DatabaseHandler.executeQuery("DELETE FROM Company");
        DatabaseHandler.executeQuery("DELETE FROM Customer");
        DatabaseHandler.executeQuery("DELETE FROM DiscountCode");
        DatabaseHandler.executeQuery("DELETE FROM Manager");
        DatabaseHandler.executeQuery("DELETE FROM Off");
        DatabaseHandler.executeQuery("DELETE FROM OffRequest");
        DatabaseHandler.executeQuery("DELETE FROM Product");
        DatabaseHandler.executeQuery("DELETE FROM ProductRequest");
        DatabaseHandler.executeQuery("DELETE FROM Review");
        DatabaseHandler.executeQuery("DELETE FROM Score");
        DatabaseHandler.executeQuery("DELETE FROM SellLog");
        DatabaseHandler.executeQuery("DELETE FROM Seller");
        DatabaseHandler.executeQuery("DELETE FROM SellerRequest");
        DatabaseHandler.executeQuery("DELETE FROM ShoppingCart");
        DatabaseHandler.executeQuery("DELETE FROM Supporter");
        DatabaseHandler.executeQuery("DELETE FROM Wallet");
        DatabaseHandler.executeQuery("DELETE FROM BankAccount");
    }

    public static void storeData(){

        for (Auction auction : Auction.getAllAuctions()) {
            insertIntoDatabase(auction, DataTypeEnum.AUCTION);
        }
        for (BuyLog buyLog : BuyLog.getAllBuyLogs().values()) {
            insertIntoDatabase(buyLog, DataTypeEnum.BUYLOG);
        }
        for (Category category : Category.getAllCategories()) {
            insertIntoDatabase(category, DataTypeEnum.CATEGORY);
        }
        for (Company company : Company.getAllCompanies().values()) {
            insertIntoDatabase(company, DataTypeEnum.COMPANY);
        }
        for (Customer customer : Customer.getaAllCustomers()) {
            insertIntoDatabase(customer, DataTypeEnum.CUSTOMER);
        }
        for (DiscountCode discountCode : DiscountCode.getAllDiscountCodes().values()) {
            insertIntoDatabase(discountCode, DataTypeEnum.DISCOUNTCODE);
        }
        for (Manager manager : Manager.getAllManagers()) {
            insertIntoDatabase(manager, DataTypeEnum.MANAGER);
        }
        for (Off off : Off.getAllOffs().values()) {
            insertIntoDatabase(off, DataTypeEnum.OFF);
        }
        for (Request request : Request.getAllRequests().values()) {
            if(request.getType().equalsIgnoreCase("Off Request")){
                insertIntoDatabase(request, DataTypeEnum.OFFREQUEST);
            } else if(request.getType().equalsIgnoreCase("Product Request")){
                insertIntoDatabase(request, DataTypeEnum.PRODUCTREQUEST);
            } else if(request.getType().equalsIgnoreCase("Seller Request")){
                insertIntoDatabase(request, DataTypeEnum.SELLERREQUEST);
            }
        }
        for (Product product : Product.getAllProducts().values()) {
            insertIntoDatabase(product, DataTypeEnum.PRODUCT);
        }
        for (Review review : Review.getAllReviews()) {
            insertIntoDatabase(review, DataTypeEnum.REVIEW);
        }
        for (Score score : Score.getAllScores()) {
            insertIntoDatabase(score, DataTypeEnum.SCORE);
        }
        for (SellLog sellLog : SellLog.getAllSellLogs().values()) {
            insertIntoDatabase(sellLog, DataTypeEnum.SELLLOG);
        }
        for (Seller seller : Seller.getAllSeller()) {
            insertIntoDatabase(seller, DataTypeEnum.SELLER);
        }
        for (ShoppingCart shoppingCart : ShoppingCart.getAllShoppingCarts()) {
            insertIntoDatabase(shoppingCart, DataTypeEnum.SHOPPINGCART);
        }
        for (Supporter supporter : Supporter.getAllSupporters()) {
            insertIntoDatabase(supporter, DataTypeEnum.SUPPORTER);
        }

        for (Off off : OffRequest.getRequestedOffs().values()) {
            insertIntoDatabase(off, DataTypeEnum.OFFATREQUEST);
        }
        for (Product product : ProductRequest.getRequestedProducts().values()) {
            insertIntoDatabase(product, DataTypeEnum.PRODUCTATREQUEST);
        }
        for (Seller seller : SellerRequest.getRequestedSellers().values()) {
            insertIntoDatabase(seller, DataTypeEnum.SELLERREQUEST);
        }
        for (Wallet wallet : Wallet.getAllWallets()) {
            insertIntoDatabase(wallet, DataTypeEnum.WALLET);
        }
        for (BankAccount bankAccount : BankAccount.getAllBankAccounts()) {
            insertIntoDatabase(bankAccount, DataTypeEnum.BANKACCOUNT);
        }
    }

    public static void reloadAllDatabases(){

        Company.reloadObjectsFromDatabase();
        Customer.reloadObjectsFromDatabase();
        DiscountCode.reloadObjectsFromDatabase();
        Manager.reloadObjectsFromDatabase();
        Off.reloadObjectsFromDatabase();
        OffRequest.reloadObjectsFromDatabase();
        Product.reloadObjectsFromDatabase();
        ProductRequest.reloadObjectsFromDatabase();
        Review.reloadObjectsFromDatabase();
        Score.reloadObjectsFromDatabase();
        SellLog.reloadObjectsFromDatabase();
        Seller.reloadObjectsFromDatabase();
        SellerRequest.reloadObjectsFromDatabase();
        ShoppingCart.reloadObjectsFromDatabase();
        Supporter.reloadObjectsFromDatabase();
        Auction.reloadObjectsFromDatabase();
        BuyLog.reloadObjectsFromDatabase();
        Category.reloadObjectsFromDatabase();
        Wallet.reloadObjectsFromDatabase();
        BankAccount.reloadObjectsFromDatabase();
    }

    public static ArrayList<Auction> selectFromAuction() {
        boolean found;
        ArrayList<Auction> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT auctionSerialized FROM Auction");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                //   or  byte[] st = rs.getBytes(1);
                //   or  Blob aBlob = rs.getBlob(1);
                //       byte[] st = aBlob.getBytes(0, (int) aBlob.length());
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Auction auction = (Auction) ois.readObject();
                arr.add(auction);
                // display the result for demonstration
//                System.out.println(auction);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }


    public static ArrayList<BuyLog> selectFromBuyLog() {
        boolean found;
        ArrayList<BuyLog> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT buyLogSerialized FROM BuyLog");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                BuyLog buyLog = (BuyLog) ois.readObject();
                arr.add(buyLog);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Category> selectFromCategory() {
        boolean found;
        ArrayList<Category> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT categorySerialized FROM Category");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Category category = (Category) ois.readObject();
                arr.add(category);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Company> selectFromCompany() {
        boolean found;
        ArrayList<Company> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT companySerialized FROM Company");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Company company = (Company) ois.readObject();
                arr.add(company);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }


    public static ArrayList<Customer> selectFromCustomer() {
        boolean found;
        ArrayList<Customer> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT customerSerialized FROM Customer");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Customer customer = (Customer) ois.readObject();
                arr.add(customer);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<DiscountCode> selectFromDiscountCode() {
        boolean found;
        ArrayList<DiscountCode> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT discountCodeSerialized FROM DiscountCode");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                DiscountCode discountCode = (DiscountCode) ois.readObject();
                arr.add(discountCode);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Manager> selectFromManager() {
        boolean found;
        ArrayList<Manager> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT managerSerialized FROM Manager");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Manager manager = (Manager) ois.readObject();
                arr.add(manager);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Off> selectFromOff() {
        boolean found;
        ArrayList<Off> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT offSerialized FROM Off");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Off off = (Off) ois.readObject();
                arr.add(off);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<OffRequest> selectFromOffRequest() {
        boolean found;
        ArrayList<OffRequest> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT offRequestSerialized FROM OffRequest");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                OffRequest offRequest = (OffRequest) ois.readObject();
                arr.add(offRequest);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Product> selectFromProduct() {
        boolean found;
        ArrayList<Product> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT productSerialized FROM Product");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Product product = (Product) ois.readObject();
                arr.add(product);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<ProductRequest> selectFromProductRequest() {
        boolean found;
        ArrayList<ProductRequest> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT productRequestSerialized FROM ProductRequest");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                ProductRequest productRequest = (ProductRequest) ois.readObject();
                arr.add(productRequest);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Review> selectFromReview() {
        boolean found;
        ArrayList<Review> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT reviewSerialized FROM Review");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Review review = (Review) ois.readObject();
                arr.add(review);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Score> selectFromScore() {
        boolean found;
        ArrayList<Score> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT scoreSerialized FROM Score");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Score score = (Score) ois.readObject();
                arr.add(score);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<SellLog> selectFromSellLog() {
        boolean found;
        ArrayList<SellLog> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sellLogSerialized FROM SellLog");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                SellLog sellLog = (SellLog) ois.readObject();
                arr.add(sellLog);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Seller> selectFromSeller() {
        boolean found;
        ArrayList<Seller> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sellerSerialized FROM Seller");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Seller seller = (Seller) ois.readObject();
                arr.add(seller);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<SellerRequest> selectFromSellerRequest() {
        boolean found;
        ArrayList<SellerRequest> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sellerRequestSerialized FROM SellerRequest");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                SellerRequest sellerRequest = (SellerRequest) ois.readObject();
                arr.add(sellerRequest);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<ShoppingCart> selectFromShoppingCart() {
        boolean found;
        ArrayList<ShoppingCart> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT shoppingCartSerialized FROM ShoppingCart");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                ShoppingCart shoppingCart = (ShoppingCart) ois.readObject();
                arr.add(shoppingCart);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Supporter> selectFromSupporter() {
        boolean found;
        ArrayList<Supporter> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT supporterSerialized FROM Supporter");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Supporter supporter = (Supporter) ois.readObject();
                arr.add(supporter);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Off> selectFromOffAtRequest() {
        boolean found;
        ArrayList<Off> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT serialized FROM OffAtRequest");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Off off = (Off) ois.readObject();
                arr.add(off);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Product> selectFromProductAtRequest() {
        boolean found;
        ArrayList<Product> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT serialized FROM ProductAtRequest");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Product product = (Product) ois.readObject();
                arr.add(product);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Seller> selectFromSellerAtRequest() {
        boolean found;
        ArrayList<Seller> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT serialized FROM SellerAtRequest");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Seller seller = (Seller) ois.readObject();
                arr.add(seller);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<Wallet> selectFromWallet() {
        boolean found;
        ArrayList<Wallet> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT walletSerialized FROM Wallet");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                Wallet wallet = (Wallet) ois.readObject();
                arr.add(wallet);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static ArrayList<BankAccount> selectFromBankAccount() {
        boolean found;
        ArrayList<BankAccount> arr = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT bankAccountSerialized FROM BankAccount");
            // loop through the result set
            while (rs.next()) {
                // fetch the serialized object to a byte array
                byte[] st = (byte[])rs.getObject(1);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                // re-create the object
                BankAccount bankAccount = (BankAccount) ois.readObject();
                arr.add(bankAccount);
            }
            stmt.close();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return arr;
    }
}
