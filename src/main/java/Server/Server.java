package Server;

import Server.ChatServers.Group.Group_Server;
import Server.ChatServers.TwoByTwo.ChatServer;
import Server.Controller.*;
import Server.Model.*;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Manager;
import Server.Model.Account.Seller;
import Server.Model.Log.BuyLog;
import Server.Model.Log.Log;
import Server.Model.Log.SellLog;
import Server.Model.Request.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Server {
    static void putToAbstract(){
        for (Seller seller : Seller.getAllSeller()) {
            Account.addAccount(seller);
        }
        for (Manager manager : Manager.getAllManagers()) {
            Account.addAccount(manager);
        }
        for (Customer customer : Customer.getaAllCustomers()) {
            Account.addAccount(customer);
        }
    }
    static void readFilesFromDatabase(){
        Control.getInstance().fillAllFiles();
        new SaveData();
        SaveData.createAllFiles();
        BuyLog.getObjectFromDatabase();
        Log.getObjectFromDatabase();
        SellLog.getObjectFromDatabase();
        Category.getObjectFromDatabase();
        DiscountCode.getObjectFromDatabase();
        Off.getObjectFromDatabase();
        Product.getObjectFromDatabase();
        Customer.getObjectFromDatabase();
        Manager.getObjectFromDatabase();
        Seller.getObjectFromDatabase();
        OffRequest.getObjectFromDatabase();
        ProductRequest.getObjectFromDatabase();
        SellerRequest.getObjectFromDatabase();
        Company.getObjectFromDatabase();
        Score.getObjectFromDatabase();
        Review.getObjectFromDatabase();
        ShoppingCart.getObjectFromDatabase();
        putToAbstract();
    }


    private static class HandleClient extends Thread{
        Socket clientSocket;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ServerSocket serverSocket;
        OutputStream outputStream;
        InputStream inputStream;
        PrintWriter printWriter;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;

        private void sendMessageToClient(String message) throws IOException {
            printWriter.println(message);
            printWriter.flush();
        }
        private void sendError(String message,boolean badError){
            String result;
            if(badError)
                result = ServerRequest.ERROR + "&" + message;
            else
                result = ServerRequest.DONE + "&"  + message;
            printWriter.println(result);
            printWriter.flush();
        }
        private String readMessageFromClient() throws IOException {
            String result;
            result = bufferedReader.readLine();
            return result;
        }
        private String getFileExt(String name){
            String extReversed = "";
            String ext = "";
            for(int i = name.length()-1;name.charAt(i) != '.';i--){
                extReversed += name.charAt(i);
            }
            for(int i = extReversed.length()-1;i>=0;i--){
                ext += extReversed.charAt(i);
            }
            return ext;
        }
        private String encodeImage(byte[] imageByteArray) {
            return Base64.getEncoder().encodeToString(imageByteArray);
        }
        private void sendImage(String path,String name) throws IOException {
            File file = new File(path);
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            String imageDataString = encodeImage(imageData);
            System.out.println("Image Successfully Manipulated!");
            JSONObject obj = new JSONObject();
            obj.put("filename",name + "." +getFileExt(path));
            obj.put("image",imageDataString);
            sendMessageToClient(obj.toJSONString());
            System.out.println("File Sent!");
        }

        private byte[] decodeImage(String imageDataString) {
            return Base64.getDecoder().decode(imageDataString);
        }

        private void receiveImage(String name) throws IOException {
            System.out.println("File Received!");
            String message = readMessageFromClient();
            JSONObject obj1 = (JSONObject) JSONValue.parse(message);
            String nametemp = obj1.get("filename").toString();
            String image = obj1.get("image").toString();
            byte[] imageByteArray = decodeImage(image);
            FileOutputStream imageOutFile = new FileOutputStream(name);
            imageOutFile.write(imageByteArray);
            imageOutFile.flush();
        }

        private File receiveFile() throws IOException {
            System.out.println("File Received!");
            String message = readMessageFromClient();
            JSONObject obj1 = (JSONObject) JSONValue.parse(message);
            String name = obj1.get("filename").toString();
            String image = obj1.get("file").toString();
            byte[] imageByteArray = decodeImage(image);
            FileOutputStream imageOutFile = new FileOutputStream("Files/" + name);
            imageOutFile.write(imageByteArray);
            imageOutFile.flush();
            File file = new File("Files/" + name);
            return file;
        }

        private void rewriteFilesRequest(){
            Seller.rewriteFiles();
            Account.rewriteFiles();
            Product.rewriteFiles();
            Off.rewriteFiles();
            ProductRequest.rewriteFiles();
            OffRequest.rewriteFiles();
            Seller.rewriteFiles();
            Category.rewriteFiles();
            Company.rewriteFiles();
        }

        HandleClient(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream,ServerSocket serverSocket,InputStream inputStream,OutputStream outputStream){
            this.clientSocket = clientSocket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.serverSocket = serverSocket;
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            printWriter = new PrintWriter(outputStream);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
        }

        @Override
        public void run() {
            String URL = "";
            while(true){
                try {
                    URL = readMessageFromClient();
//                    URL = dataInputStream.readUTF();
                    System.out.println(URL);
                    String[] parsedURL = URL.trim().split(" - ");
                    String token = parsedURL[0];
                    String request = parsedURL[1];
                    String data = parsedURL[2];

                    if(request.equalsIgnoreCase(ServerRequest.LOGIN.toString())){
                        String[] dataParsed = data.split("&");
                        String username = dataParsed[0];
                        String password = dataParsed[1];
                        try {
                            Control.getInstance().login(username,password);
                            String generatedToken = Control.getInstance().randomString(10);
                            ServerCenter.getInstance().addToken(generatedToken,Account.getAllAccounts().get(username));
                            sendMessageToClient(ServerRequest.DONE.toString() + "&" + generatedToken + "&" + Account.getAllAccounts().get(username).getFirstName());
                        } catch (Control.notFoundUserOrPass notFoundUserOrPass) {
                            sendMessageToClient(notFoundUserOrPass.getMessage());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.HASMANAGER.toString())){
                        if(Manager.getAllManagers().isEmpty()){
                            sendMessageToClient(ServerRequest.TRUE.toString());
                        }
                        else{
                            sendMessageToClient(ServerRequest.FALSE.toString());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETACCOUNTTYPE.toString())){
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        System.out.println(account.getType());
                        if(account instanceof Customer)
                            sendMessageToClient("Customer");
                        if(account instanceof Manager)
                            sendMessageToClient("Manager");
                        if(account instanceof Seller)
                            sendMessageToClient("Seller");
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.SIGNOUT.toString())){
//                        Control.getInstance().logout();
                        ServerCenter.getInstance().expireToken(token);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETPROFILEPHOTO.toString())){
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        sendImage(account.getImagePath(),"Account:" + account.getUsername());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETNAME.toString())){
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        sendMessageToClient(account.getFirstName());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETCUSTOMERINFOS.toString())){
                        Customer account =(Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        sendMessageToClient(account.getUsername() + " - "+account.getFirstName()+" - "+account.getLastName()+" - "+account.getAddress()+" - "
                        +account.getPhoneNumber()+" - "+account.getEmail()+" - "+account.getPassword()+" - "+account.getBalance());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETMANAGERINFO.toString())){
                        Manager account =(Manager) ServerCenter.getInstance().getAccountFromToken(token);
                        sendMessageToClient(account.getUsername() + " - "+account.getFirstName()+" - "+account.getLastName()+" - "+account.getEmail()+" - "
                                +account.getAddress()+" - "+account.getPhoneNumber()+" - "+account.getPassword());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSELLERINFO.toString())){
                         Seller account =(Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        sendMessageToClient(account.getUsername() + " - "+account.getFirstName()+" - "+account.getLastName()+" - "+account.getEmail()+" - "
                                +account.getAddress()+" - "+account.getPhoneNumber()+" - "+account.getPassword()+" - "+account.getCompanyName() + " - " + account.getCompanyAddress()+" - "+ account.getCredit());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETPRODUCTSPAGE.toString())){
                        String output = "NONE";
                        int i = 0;
                        for (String s : Product.getAllProducts().keySet()) {
                            if(i != 0)
                                output += " - ";
                            else
                                output = "";
                            Product product = Product.getAllProducts().get(s);
                            output += product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                    product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId();
                            i++;
                        }
                        sendMessageToClient(output);
                        for (String s : Product.getAllProducts().keySet()) {
                            sendImage(Product.getAllProducts().get(s).getImagePath(),"Product:" + Product.getAllProducts().get(s).getProductId());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETUSERHASSCORED.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        Product product = Product.getAllProducts().get(data);
                        ServerRequest respond = ServerRequest.FALSE;
                        for (Score score : product.getScoresList()) {
                            if(score.getUser().equals(customer)){
                                respond = ServerRequest.TRUE;
                            }
                        }
                        sendMessageToClient(respond.toString());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETPRODUCTOFF.toString())){
                        Product product = Product.getAllProducts().get(data);
                        if(product.getOff() == null)
                            sendMessageToClient(ServerRequest.NULL.toString());
                        else{
                            Off off = product.getOff();
                            sendMessageToClient(off.getOffId() + "&" + off.getOffAmount());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETPRODUCTREVIEWS.toString())){
                        Product product = Product.getAllProducts().get(data);
                        String response = "";
                        int i = 0;
                        for (Review review : product.getReviewsList()) {
                            if(i != 0)
                                response += " - ";
                            response += review.getUser().getUsername() + ": " + review.getReviewText();
                            i++;
                        }
                        sendMessageToClient(response);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETPRODUCTSELLERS.toString())){
                        Product product = Product.getAllProducts().get(data);
                        String response = "";
                        int i = 0;
                        for (Seller seller : product.getSellers()) {
                            if(i != 0)
                                response += " - ";
                            response += seller.getUsername();
                            i++;
                        }
                        sendMessageToClient(response);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTSCORE.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        String[] parsedData = data.split("&");
                        Product product = Product.getAllProducts().get(parsedData[0]);
                        int score = Integer.parseInt(parsedData[1]);
                        product.addScore(new Score(customer,product,score));
                        sendMessageToClient(ServerRequest.DONE.toString() + "&" + product.getBuyersAverageScore());
                        Product.rewriteFiles();
                        Score.rewriteFiles();
                        Customer.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEADDPRODUCTTOCART.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        ShoppingCart cart = customer.getShoppingCart();
                        String[] parsedData = data.split("&");
                        Product product = Product.getAllProducts().get(parsedData[0]);
                        Seller seller = (Seller) Account.getAllAccounts().get(parsedData[1]);
                        if(!cart.getProductsQuantity().containsKey(product)){
                            try {
                                cart.addProduct(product,1,seller);
                                sendMessageToClient(ServerRequest.DONE + "&Product has added to your cart");
                            } catch (Exception e) {
                                sendMessageToClient(ServerRequest.ERROR + "&" + e.getMessage());
                            }
                        }
                        else{
                            cart.increaseQuantity(product);
                            sendMessageToClient(ServerRequest.DONE + "&Now you have " + cart.getProductsQuantity().get(product) + " of this product.");
                        }

                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEREMOVEPRODUCTTOCART.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        ShoppingCart cart = customer.getShoppingCart();
                        Product product = Product.getAllProducts().get(data);
                        if(cart.getProductsQuantity().containsKey(product)){
                            cart.decreaseQuantity(product);
                            sendMessageToClient(ServerRequest.DONE+"&Now you have " + cart.getProductsQuantity().get(product) + " of this product");
                        }
                        else{
                            sendMessageToClient(ServerRequest.ERROR+"&You don't have this product.");
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTREVIEW.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        String[] parsedData = data.split("//");
                        Product product = Product.getAllProducts().get(parsedData[0]);
                        product.addReview(new Review(customer,product,parsedData[1],customer.hasBought(product)));
                        sendMessageToClient(ServerRequest.DONE.toString());
                        Product.rewriteFiles();
                        Review.rewriteFiles();
                        Customer.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEINCREASECART.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        ShoppingCart shoppingCart = customer.getShoppingCart();
                        Product product = Product.getAllProducts().get(data);
                        shoppingCart.increaseQuantity(product);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEDECREASECART.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        ShoppingCart shoppingCart = customer.getShoppingCart();
                        Product product = Product.getAllProducts().get(data);
                        shoppingCart.decreaseQuantity(product);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEREMOVECART.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        ShoppingCart shoppingCart = customer.getShoppingCart();
                        Product product = Product.getAllProducts().get(data);
                        shoppingCart.removeProduct(product);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSHOPPINGCART.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        ShoppingCart shoppingCart = customer.getShoppingCart();
                        String output = "NONE";
                        int i = 0;
                        for (Product product : shoppingCart.getProductsQuantity().keySet()) {
                            if(i != 0)
                                output += " - ";
                            else
                                output = "";
                            output += product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                    product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId() + "&" + shoppingCart.getProductsQuantity().get(product);
                            i++;
                        }
                        sendMessageToClient(output);
                        for (Product product : shoppingCart.getProductsQuantity().keySet()) {
                            sendImage(product.getImagePath(),"Product:" + product.getProductId());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETOFFSINFO.toString())){
                        Off off = Off.getAllOffs().get(data);
                        sendMessageToClient(off.getOffAmount() + "&"+off.getStartTime() + "&"+off.getEndTime() + "&" + off.getOffId());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETOFFSPRODUCTS.toString())){
                        Off off = Off.getAllOffs().get(data);
                        String output = "NONE";
                        int i = 0;
                        for (Product product : off.getProductsList()) {
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            output += product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                    product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId();
                        i++;
                        }
                        sendMessageToClient(output);
                        for (Product product : off.getProductsList()) {
                            sendImage(product.getImagePath(),"Product:" + product.getProductId());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLOFFS.toString())){
                        String output = "NONE";
                        int i = 0;
                        for (String s : Off.getAllOffs().keySet()) {
                            Off off = Off.getAllOffs().get(s);
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            output += off.getOffAmount() +"&"+off.getStartTime()+"&"+off.getEndTime()+"&"+off.getOffId();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSELLERPRODUCTS.toString())){
                        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        String output = "NONE";
                        for (int i = 0; i < seller.getAllProducts().size(); i++) {
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            Product product = seller.getAllProducts().get(i);
                            output += product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                    product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId();
                        }
                        sendMessageToClient(output);
                        for (Product product : seller.getAllProducts()) {
                            sendImage(product.getImagePath(), "Product:"+product.getProductId());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTREMOVEPRODUCTREQ.toString())){
                        try {
                            String reqID = ControlSeller.getInstance().sendRemoveProductReq(data,token);
                            sendError(reqID,false);
                            Product.rewriteFiles();
                            ProductRequest.rewriteFiles();
                            Manager.rewriteFiles();
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSELLERGOTPRODUCT.toString())){
                        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        String productId = data;
                        if(ControlSeller.getInstance().checkProductExists(productId) && ControlSeller.getInstance().checkSellerGotProduct(productId, seller))
                            sendMessageToClient(ServerRequest.TRUE.toString());
                        else
                            sendMessageToClient(ServerRequest.FALSE.toString());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTADDPRODUCTTOSELLERLIST.toString())){
                        try {
                            String reqID = ControlSeller.getInstance().sendAddSellerProductReq(data,token);
                            sendError(reqID,false);
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                        ProductRequest.rewriteFiles();
                        Product.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETPRODUCT.toString())){
                        Product product = Product.getAllProducts().get(data);
                        String output = product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId();
                        sendMessageToClient(output);
                        sendImage(product.getImagePath(),"Product:"+product.getProductId());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTEDITPRODUCTREQ.toString())){
                        String[] parsedData = data.split("//");
                        String productID = parsedData[0];
                        String field = parsedData[1];
                        String newValue = parsedData[2];
                        try {
                            String reqID = ControlSeller.getInstance().sendProductEditReq(productID,field,newValue,token);
                            sendError(reqID,false);
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                        Product.rewriteFiles();
                        ProductRequest.rewriteFiles();
                        Manager.rewriteFiles();
                        Seller.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETBUYERSPRODUCT.toString())){
                        String productId = data;
                        Product product = Product.getAllProducts().get(productId);
                        String output = "NONE";
                        int i=0;
                        for (Customer buyer : product.getBuyers()) {
                            if(i!=0)
                                output += "&";
                            else
                                output = "";
                            output += buyer.getUsername();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTCREATEPRODUCTREQ.toString())){
                        System.out.println(data);
                        String[] parsedData = data.split("//");
                        String name = parsedData[0];
                        String companyName = parsedData[1];
                        String category = parsedData[2];
                        String price = parsedData[3];
                        String companyLocation = parsedData[4];
                        String fileExt = parsedData[5];
                        String productId = Control.getInstance().randomString(10);
                        String imagePath = "productPhotos/product.png";
                        if(!fileExt.equalsIgnoreCase("NULL")){
                            imagePath = "productPhotos/" + productId +"."+ fileExt;
                            receiveImage(imagePath);
                        }
                        try {
                            String reqID = ControlSeller.getInstance().sendAddProductReq(name,companyName,category,price,companyLocation,productId,imagePath,token);
                            sendError(reqID,false);
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                            if(!fileExt.equalsIgnoreCase("NULL")){
                                File file = new File(imagePath);
                                file.delete();
                            }
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETBUYLOGS.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        String output = "NONE";
                        int i = 0;
                        for (BuyLog buyLog : customer.getBuyLogs()) {
                            if(i!=0)
                                output+=" - ";
                            else
                                output = "";
                            output += buyLog.getLogId() + "&" + buyLog.getTotalDiscountAmount() + "&" + buyLog.getPrice() + "&" + buyLog.getDate() + "&" + buyLog.getReceiverAddress() + "&" + buyLog.getReceiverPhoneNo() + "&" + buyLog.getReceiverName();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETBUYLOGPRODUCTS.toString())){
                        String output = "NONE";
                        int i =0;
                        BuyLog buyLog = BuyLog.getAllBuyLogs().get(data);
                        for (Product product : buyLog.getAllProducts()) {
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            output += product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                    product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId();
                            i++;
                        }
                        sendMessageToClient(output);
                        for (Product product : buyLog.getAllProducts()) {
                            sendImage(product.getImagePath(),"Product:"+product.getProductId());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSELLLOGS.toString())){
                        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        String output = "NONE";
                        int i = 0;
                        for (SellLog sellLog : seller.getSellLogs()) {
                            if(i!=0)
                                output+=" - ";
                            else
                                output = "";
                            output += sellLog.getLogId() + "&" + sellLog.getTotalDiscountAmount() + "&" + sellLog.getPrice() + "&" + sellLog.getDate() + "&" + sellLog.getReceiverAddress() + "&" + sellLog.getReceiverPhoneNo() + "&" + sellLog.getReceiverName();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSELLLOGPRODUCTS.toString())){
                        String output = "NONE";
                        int i =0;
                        SellLog sellLog = SellLog.getAllSellLogs().get(data);
                        for (Product product : sellLog.getAllProducts()) {
                            if(product != null){
                                if(i!=0)
                                    output += " - ";
                                else
                                    output = "";
                                System.out.println(product);
                                output += product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                        product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId();
                                i++;
                            }
                        }
                        sendMessageToClient(output);
                        for (Product product : sellLog.getAllProducts()) {
                            sendImage(product.getImagePath(),"Product:"+product.getProductId());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSELLEROFFS.toString())){
                        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        String output = "NONE";
                        int i = 0;
                        for (Off off : seller.getAllOffs()) {
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            output += off.getOffAmount() +"&"+off.getStartTime()+"&"+off.getEndTime()+"&"+off.getOffId();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETSELLERGOTOFF.toString())){
                        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        if(ControlSeller.getInstance().checkOffExistance(data) && ControlSeller.getInstance().checkSellerGotOff(data,seller))
                            sendMessageToClient(ServerRequest.TRUE.toString());
                        else
                            sendMessageToClient(ServerRequest.FALSE.toString());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETCUSTOMERDISCOUNTCODES.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        String output = "NONE";
                        int i = 0;
                        for (String s : customer.getDiscountCodes().keySet()) {
                            DiscountCode discountCode = DiscountCode.getAllDiscountCodes().get(s);
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            output += discountCode.getDiscountId() + "&" + discountCode.getDiscountPercentage() + "&" + discountCode.getDiscountNumberForEachUser() + "&" +discountCode.getMaxDiscountAmount();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEFIELDMANAGER.toString())){
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        String[] allChanges = data.split("//");
                        for (String change : allChanges) {
                            String[] parsed = change.split("&");
                            String field = parsed[0];
                            String newValue = parsed[1];
                            if(field.equalsIgnoreCase("firstname"))
                                account.setFirstName(newValue);
                            else if(field.equalsIgnoreCase("lastname"))
                                account.setLastName(newValue);
                            else if(field.equalsIgnoreCase("phone"))
                                account.setPhoneNumber(newValue);
                            else if(field.equalsIgnoreCase("email"))
                                account.setEmail(newValue);
                            else if(field.equalsIgnoreCase("address"))
                                account.setAddress(newValue);
                            else if(field.equalsIgnoreCase("password"))
                                account.setPassword(newValue);
                        }
                        Account.rewriteFiles();
                        Customer.rewriteFiles();
                        Seller.rewriteFiles();
                        Manager.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLCATEGORIES.toString())){
                        String output = "NONE";
                        int i = 0;
                        for (Category category : Category.getAllCategories()) {
                            if(i != 0)
                                output += " - ";
                            else
                                output = "";
                            output += category.getName();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTREMOVECATEGORY.toString())){
                        boolean found = false;
                        for (Category category : Category.getAllCategories()) {
                            if(category.getName().equalsIgnoreCase(data)) {
                                Category.removeCategory(category);
                                sendError("Found", false);
                                found = true;
                                break;
                            }
                        }
                        if(!found)
                            sendError("Not found",true);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETCATEGORYEXISTS.toString())){
                        if(ControlManager.getInstance().checkCategoryExistance(data))
                            sendError("Found",false);
                        else
                            sendError("Not Found",true);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTCREATECATEGORY.toString())){
                        String[] message = data.split("//");
                        String products = message[0];
                        String name = message[1];
                        System.out.println(data);

                        ArrayList<Product> productsExist = new ArrayList<>();
                        ArrayList<String> productsNotExist = new ArrayList<>();
                        String notExist = "NONE";

                        if(!products.equalsIgnoreCase("NULL")){
                            String[] productIDs = products.split("&");
                            for (String productID : productIDs) {
                                if(Product.getAllProducts().containsKey(productID)){
                                    productsExist.add(Product.getAllProducts().get(productID));
                                }
                                else {
                                    productsNotExist.add(productID);
                                }
                            }
                            int i = 0;
                            for (String s : productsNotExist) {
                                if(i!=0)
                                    notExist += ",";
                                else
                                    notExist = "";
                                notExist += s;
                                i++;
                            }
                        }
                        try {
                            ControlManager.getInstance().addCategory(name,productsExist);
                            sendError(notExist,false);
                            Product.rewriteFiles();
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEEDITCATEGORY.toString())){
                        System.out.println(data);
                        String[] parsedData = data.split("//");
                        ArrayList<String> oks = new ArrayList<>();
                        ArrayList<String> errors = new ArrayList<>();
                        for (String s : parsedData) {
                            String field = s.split("&")[0];
                            String value = s.split("&")[1];
                            String editCategory = s.split("&")[2];
                            if(field.equalsIgnoreCase("name")){
                                try {
                                    ControlManager.getInstance().changeCategoryName(value,editCategory);
                                    oks.add("Name");
                                } catch (Exception e) {
                                    errors.add("Name: " + e.getMessage());
                                }
                            }
                            else if(field.equalsIgnoreCase("addproduct")){
                                if(!Product.getAllProducts().containsKey(value)){
                                        errors.add("Add Product: This product doesn't exist!");
                                    }else{
                                        for (Category category : Category.getAllCategories()) {
                                            if(category.getName().equalsIgnoreCase(editCategory))
                                            {
                                                category.addProductToCategory(Product.getAllProducts().get(value));
                                                oks.add("Add Product: Added");
                                            }
                                        }
                                    }
                            }
                            else if(field.equalsIgnoreCase("removeproduct")){
                                if(!Product.getAllProducts().containsKey(value)){
                                        errors.add("Remove Product: This product doesn't exist!");
                                    }else{
                                        for (Category category : Category.getAllCategories()) {
                                            if(category.getName().equalsIgnoreCase(editCategory))
                                            {
                                                if(category.getProductsList().contains(Product.getAllProducts().get(value))){
                                                    category.removeProductFromCategory(Product.getAllProducts().get(value));
                                                    oks.add("Remove Product: Removed");
                                                }
                                                else
                                                    errors.add("Remove product: This product isn't in this category!");
                                            }
                                        }
                                    }
                            }
                        }
                        String output = " ";
                        int i = 0;
                        for (String ok : oks) {
                            if(i!=0)
                                output += "&";
                            output += ok;
                            i++;
                        }
                        output += " -  ";
                        i = 0;
                        for (String error : errors) {
                            if(i!=0)
                                output += "&";
                            output += error;
                            i++;
                        }
                        sendMessageToClient(output);

                        Category.rewriteFiles();
                        Product.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLACCOUNTS.toString())){
                        String output = "NONE";
                        int i = 0;
                        for (String s : Account.getAllAccounts().keySet()) {
                            if(i != 0)
                                output += " - ";
                            else
                                output = "";
                            output += s + "&" + Account.getAllAccounts().get(s).getType();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETCHECKUSEREXISTS.toString())){
                        if(Account.getAllAccounts().keySet().contains(data)){
                            sendMessageToClient(ServerRequest.TRUE.toString());
                        }else{
                            sendMessageToClient(ServerRequest.FALSE.toString());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTREMOVEUSER.toString())){
                        try{
                            Control.getInstance().deleteUser(data,token);
                            sendError("Deleted",false);
                            Account.rewriteFiles();
                            Customer.rewriteFiles();
                            Manager.rewriteFiles();
                            Seller.rewriteFiles();
                        }catch (Exception e){
                            sendError(e.getMessage(),true);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTCREATEMANAGERPROFILE.toString())){
                        String[] parsedData = data.split("//");
                        String username = parsedData[0];
                        String password = parsedData[1];
                        String firstName = parsedData[2];
                        String lastName = parsedData[3];
                        String email = parsedData[4];
                        String phoneNumber = parsedData[5];
                        String confirmPassword = parsedData[6];
                        String fileExt = parsedData[7];
                        String imagePath = "profilePhotos/account_icon.png";
                        if(!fileExt.equalsIgnoreCase("NULL")){
                            imagePath = "profilePhotos/" + username + "." + fileExt;
                            receiveImage(imagePath);
                        }
                        try {
                            Control.getInstance().createAccount("Manager",username,password,firstName,lastName,
                                    email,phoneNumber,confirmPassword,null,false,imagePath,token);
                            sendError("DONE",false);
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                            if(!fileExt.equalsIgnoreCase("NULL")){
                                File file = new File(imagePath);
                                file.delete();
                            }
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.SIGNUP.toString())){
                        String[] parsedData = data.split("//");
                        String username = parsedData[0];
                        String password = parsedData[1];
                        String firstName = parsedData[2];
                        String lastName = parsedData[3];
                        String email = parsedData[4];
                        String phoneNumber = parsedData[5];
                        String confirmPassword = parsedData[6];
                        String fileExt = parsedData[7];
                        String type = parsedData[8];
                        boolean login = true;
                        if(type.equalsIgnoreCase("Seller"))
                            login = false;
                        String imagePath = "profilePhotos/account_icon.png";
                        if(!fileExt.equalsIgnoreCase("NULL")){
                            imagePath = "profilePhotos/" + username + "." + fileExt;
                            receiveImage(imagePath);
                        }
                        try {
                            Control.getInstance().createAccount(type,username,password,firstName,lastName,
                                    email,phoneNumber,confirmPassword,null,login,imagePath,token);
                            String generatedToken = Control.getInstance().randomString(10);
                            if(login){
                                ServerCenter.getInstance().addToken(generatedToken,Account.getAllAccounts().get(username));
                                System.out.println("token added");
                            }
                            sendError(generatedToken,false);

                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                            if(!fileExt.equalsIgnoreCase("NULL")){
                                File file = new File(imagePath);
                                file.delete();
                            }
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTCREATECOMPANY.toString())){
                        String[] parsedData = data.split("//");
                        String username = parsedData[0];
                        String companyName = parsedData[1];
                        String companyAddress = parsedData[2];
                        Company company;
                        if(Company.getAllCompanies().containsKey(companyName))
                        {
                            company = Company.getAllCompanies().get(companyName);
                        }
                        else
                        {
                            company = new Company(companyName,companyAddress);
                        }
                        for (String s : SellerRequest.getRequestedSellers().keySet()) {
                            if(username.equals(SellerRequest.getRequestedSellers().get(s).getUsername())){
                                SellerRequest.getRequestedSellers().get(s).setCompany(company);
                                break;
                            }
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTMAKEMANAGERFIRST.toString())){
                        String[] parsedData = data.split("//");
                        String username = parsedData[0];
                        String password = parsedData[1];
                        String firstName = parsedData[2];
                        String lastName = parsedData[3];
                        String email = parsedData[4];
                        String phoneNumber = parsedData[5];
                        String confirmPassword = parsedData[6];
                        String fileExt = parsedData[7];
                        String imagePath = "profilePhotos/account_icon.png";
                        if(!fileExt.equalsIgnoreCase("NULL")){
                            imagePath = "profilePhotos/" + username + "." + fileExt;
                            receiveImage(imagePath);
                        }
                        try {
                            Control.getInstance().createAccount("Manager",username,password,firstName,lastName,
                                    email,phoneNumber,confirmPassword,null,true,imagePath,token);
                            String generatedToken = Control.getInstance().randomString(10);
                            ServerCenter.getInstance().addToken(generatedToken,Account.getAllAccounts().get(username));
                            sendError(generatedToken,false);
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                            if(!fileExt.equalsIgnoreCase("NULL")){
                                File file = new File(imagePath);
                                file.delete();
                            }
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLDISCOUNTCODES.toString())){
                        String output = "NONE";
                        int i=0;
                        for (String s : DiscountCode.getAllDiscountCodes().keySet()) {
                            DiscountCode discountCode = DiscountCode.getAllDiscountCodes().get(s);
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            output += discountCode.getDiscountId() + "&" + discountCode.getDiscountPercentage() + "&" + discountCode.getDiscountNumberForEachUser() + "&" + discountCode.getMaxDiscountAmount();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETDISCOUNTCODEEXISTS.toString())){
                        if(DiscountCode.getAllDiscountCodes().containsKey(data))
                            sendMessageToClient(ServerRequest.TRUE.toString());
                        else
                            sendMessageToClient(ServerRequest.FALSE.toString());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTREMOVEDISCOUNTCODE.toString())){
                        try {
                            ControlManager.getInstance().removeDiscountCode(data);
                            sendError("Done",false);
                            Customer.rewriteFiles();
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTCREATEDISCOUNTCODE.toString())){
                        String discountCode = Control.getInstance().randomString(10);
                        String[] parsedData = data.split("//");
                        String[] primaryData = parsedData[0].split("&");
                        String[] usersToAdd;
                        ArrayList<String> usersNotExist = new ArrayList<>();
                        ArrayList<String> usersNotCustomer = new ArrayList<>();
                        HashMap<String,Customer> codeOwners = new HashMap<>();

                        if(!parsedData[1].equalsIgnoreCase("NULL")){
                            usersToAdd = parsedData[1].split(",");
                            for (String owner : usersToAdd){
                                owner = owner.trim();
                                if(!Account.getAllAccounts().containsKey(owner)){
                                    usersNotExist.add(owner);
                                    continue;
                                }
                                if(!(Account.getAllAccounts().get(owner) instanceof Customer))
                                {
                                    usersNotCustomer.add(owner);
                                    continue;
                                }
                                codeOwners.put(owner,(Customer) Account.getAllAccounts().get(owner));
                            }
                        }
                        try {
                            ControlManager.getInstance().createDiscountCode(discountCode,primaryData[0],primaryData[1]
                                    ,primaryData[2],primaryData[3],primaryData[4],codeOwners);
                            String notExist = "NONE";
                            int i=0;
                            for (String s : usersNotExist) {
                                if(i!=0)
                                    notExist += "&";
                                else
                                    notExist = "";
                                notExist += s;
                                i++;
                            }
                            String notCustomer = "NONE";
                            i=0;
                            for (String s : usersNotCustomer) {
                                if(i!=0)
                                    notExist += "&";
                                else
                                    notExist = "";
                                notCustomer += s;
                                i++;
                            }
                            sendMessageToClient(ServerRequest.DONE.toString() + " - " + discountCode + " - " + notExist + " - " + notCustomer);
                            Customer.rewriteFiles();
                            Product.rewriteFiles();
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEEDITDISCOUNTCODE.toString())){
                        ArrayList<String> oks = new ArrayList<>();
                        ArrayList<String> errors = new ArrayList<>();
                        String[] parsedData = data.split("//");
                        for (String s : parsedData) {
                            String field = s.split("&")[0];
                            String value = s.split("&")[1];
                            String code = s.split("&")[2];
                            if(field.equalsIgnoreCase("startTime")){
                                try {
                                    ControlManager.getInstance().editDiscountCode("Start date",value,code);
                                    oks.add("Start time");
                                } catch (Exception e) {
                                    errors.add("Start time: "+ e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("endTime")){
                                try {
                                    ControlManager.getInstance().editDiscountCode("End date",value,code);
                                    oks.add("End time");
                                } catch (Exception e) {
                                    errors.add("End time: "+ e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("percentage")){
                                try {
                                    ControlManager.getInstance().editDiscountCode("Percentage",value,code);
                                    oks.add("Percentage");
                                } catch (Exception e) {
                                    errors.add("Percentage: " + e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("maxTimes")){
                                try {
                                    ControlManager.getInstance().editDiscountCode("Max times",value,code);
                                    oks.add("Max per user");
                                } catch (Exception e) {
                                    errors.add("Max per user: " + e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("addOwner")){
                                try {
                                    ControlManager.getInstance().editDiscountCode("add owner",value,code);
                                    oks.add("Add user");
                                } catch (Exception e) {
                                    errors.add("Add user: "+e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("removeOwner")){
                                try {
                                    ControlManager.getInstance().editDiscountCode("remove owner",value,code);
                                    oks.add("Remove user");
                                } catch (Exception e) {
                                    errors.add("Remove user: " + e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("maxPercentage")){
                                try {
                                    ControlManager.getInstance().editDiscountCode("Max amount",value,code);
                                    oks.add("Max percentage");
                                } catch (Exception e) {
                                    errors.add("Max percentage: " + e.getMessage());
                                }
                            }
                        }
                            String oksString = "NONE";
                            int i = 0;
                            for (String ok : oks) {
                                if(i!=0)
                                    oksString += "&";
                                else
                                    oksString = "";
                                oksString += ok;
                                i++;
                            }
                            String errorsString = "NONE";
                            i = 0;
                            for (String ok : errors) {
                                if(i!=0)
                                    errorsString += "&";
                                else
                                    errorsString = "";
                                errorsString += ok;
                                i++;
                            }
                            sendMessageToClient(oksString + " - " + errorsString);
                            Customer.rewriteFiles();
                            DiscountCode.rewriteFiles();
                            Product.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLPRODUCTS.toString())){
                        String output = "NONE";
                        int i=0;
                        for (String s : Product.getAllProducts().keySet()) {
                            Product product = Product.getAllProducts().get(s);
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            output += product.getName() + "&" + product.getProductId();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTREMOVEPRODUCT.toString())){
                        try {
                            ControlManager.getInstance().removeProduct(data);
                            sendError("DONE",false);
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                        Category.rewriteFiles();
                        Company.rewriteFiles();
                        Seller.rewriteFiles();
                        Customer.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLREQUESTS.toString())){
                        String output = "NONE";
                        int i=0;
                        for (String s : Request.getAllRequests().keySet()) {
                            Request req = Request.getAllRequests().get(s);
                            if(i!=0)
                                output+=" - ";
                            else
                                output="";
                            output += req.getRequestId() + "&" + req.getProviderUsername() + "&" + req.getType();
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTACCEPTREQUEST.toString())){
                        String requestId = data;
                        String output = "";
                        if(ControlManager.getInstance().checkRequestIdExistance(requestId))
                        {
                            if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.ADD))
                            {
                                output = ServerRequest.DONE + "&It has been successfully added!";
                            }
                            else if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.DELETE))
                            {
                                output = ServerRequest.DONE + "&It has been successfully deleted!";
                            }
                            else if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.EDIT))
                            {
                                output = ServerRequest.DONE + "&It has been successfully edited!";
                            }
                            else if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.ADD_SELLER))
                            {
                                output = ServerRequest.DONE + "&It has been successfully added!";
                            }
                            Request.getAllRequests().get(requestId).acceptReq(requestId);
                            rewriteFilesRequest();
                        }else{
                            output = ServerRequest.ERROR + "&This request ID doesn't exist!";
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTDECLINEREQUEST.toString())){
                        String requestId = data;
                        String output = "";
                        if(ControlManager.getInstance().checkRequestIdExistance(requestId))
                        {
                            Request.getAllRequests().get(requestId).declineReq(requestId);
                            output = ServerRequest.DONE + "&It has been declined successfully!";
                            rewriteFilesRequest();
                        }
                        else
                        {
                            output = ServerRequest.ERROR + "&This request ID doesn't exist!";
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETCHECKREQUESTEXISTS.toString())){
                        String output;
                        if(ControlManager.getInstance().checkRequestIdExistance(data)){
                            output = ServerRequest.TRUE.toString();
                        }else{
                            output = ServerRequest.TRUE.toString();
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETDISCOUNTCODEINFOS.toString())){
                        DiscountCode discountCode = DiscountCode.getAllDiscountCodes().get(data);
                        String output = "NONE";
                        int i = 0;
                        for (String s : discountCode.getDiscountOwners().keySet()) {
                            if(i!=0)
                                output += "&";
                            else
                                output = "";
                            output += s;
                            i++;
                        }
                        sendMessageToClient(output + " - " + discountCode.getDiscountPercentage() + " - " + discountCode.getStartTime().toString() + " - " +
                                discountCode.getEndTime().toString() + " - " + discountCode.getMaxDiscountAmount() + " - " + discountCode.getDiscountNumberForEachUser());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETREQUESTINFOS.toString())){
                        Request req = Request.getAllRequests().get(data);
                        String output = "";
                        if(req instanceof SellerRequest)
                        {

                            Seller seller = Request.getRequestedSellers().get(data);
                            output = "A seller with these infos is waiting for you!" + " - " + "Username" + " - " + "Name" + " - " + "Email" +  " - " + "Phone Number" +
                                    " - " + "Company" + " - "  + "Password" + " - " + seller.getUsername() + " - " + seller.getFirstName() + " " + seller.getLastName() +
                                    " - " + seller.getEmail() + " - " + seller.getPhoneNumber() + " - " + seller.getCompanyName() + " - " + seller.getPassword();
                        }
                        else if(req instanceof OffRequest)
                        {

                            Off off = Request.getRequestedOffs().get(data);
                            output = "An Off with these infos is waiting for you!" + " - " + "Request Type" + " - " + "OFF ID" + " - " + "Start Time" + " - " +
                                    "End Time" + " - " + "Off Percentage" + " - " + " " + " - " + req.getType() + " " + req.getRequestType() + " - " +
                                    off.getOffId() + " - " + off.getStartTime().toString() + " - " + off.getEndTime().toString() + " - " + off.getOffAmount()
                            + " - " + " ";
                        }
                        else if(req instanceof ProductRequest)
                        {
                            Product product = Request.getRequestedProducts().get(data);
                            output = "An Product with these infos is waiting for you!" + " - " + "Request Type" + " - " + "Name" + " - " + "ID" + " - " +
                                    "Company Name" + " - " + "Price" + " - " + "Category" + " - " + req.getType() + " " + req.getRequestType() + " - " +
                                    product.getName() + " - " + product.getProductId() + " - " + product.getCompany().getName() + " - " + product.getPrice()
                                    + " - " + product.getCategory().getName();
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETUSERINFOS.toString())){
                        Account account = Account.getAllAccounts().get(data);
                        String output = account.getPhoneNumber() + " - " + account.getEmail() + " - " + account.getFirstName() + " " +account.getLastName() + " - "+
                                account.getAddress() + " - " + account.getUsername() + " - " + account.getPassword();
                        sendMessageToClient(output);
                        sendImage(account.getImagePath(),"Account:" + account.getUsername());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTCREATEOFF.toString())){
                        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        String[] parseData = data.split("//");
                        ArrayList<Product> products = new ArrayList<>();
                        String[] productIDs = parseData[0].split(",");
                        ArrayList<String> productsNotExist = new ArrayList<>();

                        for (String productID : productIDs){
                            productID = productID.trim();
                            if (!ControlSeller.getInstance().checkProductExists(productID) || !ControlSeller.getInstance().checkSellerGotProduct(productID, seller))
                            {
                                productsNotExist.add(productID);
                                continue;
                            }
                            products.add(Product.getAllProducts().get(productID));
                        }

                        try {
                            String reqID = ControlSeller.getInstance().sendAddOfRequest(products,parseData[1],parseData[2],parseData[3],token);
                            String output = "NONE";
                            int i = 0;
                            for (String s : productsNotExist) {
                                if(i != 0)
                                    output += ",";
                                else
                                    output = "";
                                output += s;
                                i++;
                            }
                            if(output.isEmpty())
                                output = "NONE";

                            sendMessageToClient(ServerRequest.DONE.toString() + "&" + reqID + "&" + output);

                            Off.rewriteFiles();
                            OffRequest.rewriteFiles();
                            Product.rewriteFiles();
                            Seller.rewriteFiles();
                            Manager.rewriteFiles();
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEEDITFIELDSELLER.toString())){
                        Seller account = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        String[] allChanges = data.split("//");
                        for (String change : allChanges) {
                            String[] parsed = change.split("&");
                            String field = parsed[0];
                            String newValue = parsed[1];
                            if(field.equalsIgnoreCase("firstname"))
                                account.setFirstName(newValue);
                            else if(field.equalsIgnoreCase("lastname"))
                                account.setLastName(newValue);
                            else if(field.equalsIgnoreCase("phone"))
                                account.setPhoneNumber(newValue);
                            else if(field.equalsIgnoreCase("email"))
                                account.setEmail(newValue);
                            else if(field.equalsIgnoreCase("address"))
                                account.setAddress(newValue);
                            else if(field.equalsIgnoreCase("password"))
                                account.setPassword(newValue);
                            else if(!account.getCompanyName().equalsIgnoreCase("Not Set")){
                                if(field.equalsIgnoreCase("companyname"))
                                    account.getCompany().setName(newValue);
                                else if(field.equalsIgnoreCase("companyaddress"))
                                    account.getCompany().setLocation(newValue);
                            }
                        }
                        Seller.rewriteFiles();
                        Account.rewriteFiles();
                        Company.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.UPDATEEDITOFFREQ.toString())){
                        Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                        ArrayList<String> oks = new ArrayList<>();
                        ArrayList<String> errors = new ArrayList<>();
                        ArrayList<String> reqIDs = new ArrayList<>();

                        String[] parsedData = data.split("//");
                        for (String s : parsedData) {
                            String field = s.split("&")[0];
                            String value = s.split("&")[1];
                            String offID = s.split("&")[2];
                            if (field.equalsIgnoreCase("percentage")){
                                try {
                                    String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"amount",value,"null",token);
                                    reqIDs.add(reqID);
                                    oks.add("Percentage");
                                } catch (Exception e) {
                                    errors.add("Percentage: "+ e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("endtime")){
                                try {
                                    String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"end",value,"null",token);
                                    reqIDs.add(reqID);
                                    oks.add("End Date");
                                } catch (Exception e) {
                                    errors.add("End Date: "+ e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("starttime")){
                                try {
                                    String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"start",value,"null",token);
                                    reqIDs.add(reqID);
                                    oks.add("Start Date");
                                } catch (Exception e) {
                                    errors.add("Start Date: "+ e.getMessage());
                                }
                            }else if(field.equalsIgnoreCase("addproduct")){
                                try {
                                    String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"add product","",value,token);
                                    reqIDs.add(reqID);
                                    oks.add("Add Product");
                                } catch (Exception e) {
                                    errors.add("Add Product: "+ e.getMessage());
                                }
                            } else if (field.equalsIgnoreCase("removeproduct")) {
                                try {
                                    if (ControlSeller.getInstance().checkProductExists(value) && ControlSeller.getInstance().checkSellerGotProduct(value, seller)) {
                                        String reqID = ControlSeller.getInstance().sendEditOffRequest(offID, "remove product", "", value,token);
                                        reqIDs.add(reqID);
                                        oks.add("Remove Product");
                                    } else {
                                        errors.add("Remove Product: You don't have this product");
                                    }
                                } catch (Exception e) {
                                    errors.add("Remove Product: " + e.getMessage());
                                }
                            }
                        }
                        String oksString = "NONE";
                        int i = 0;
                        for (String ok : oks) {
                            if(i!=0)
                                oksString += "&";
                            else
                                oksString = "";
                            oksString += ok;
                            i++;
                        }
                        String errorsString = "NONE";
                        i = 0;
                        for (String ok : errors) {
                            if(i!=0)
                                errorsString += "&";
                            else
                                errorsString = "";
                            errorsString += ok;
                            i++;
                        }
                        String reqIDsString = "NONE";
                        i = 0;
                        for (String ok : reqIDs) {
                            if(i!=0)
                                reqIDsString += "&";
                            else
                                reqIDsString = "";
                            reqIDsString += ok;
                            i++;
                        }
                        sendMessageToClient(oksString + " - " + errorsString + " - " + reqIDsString);

                        Off.rewriteFiles();
                        OffRequest.rewriteFiles();
                        Manager.rewriteFiles();
                        Seller.rewriteFiles();
                        Product.rewriteFiles();
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTPAYMENT.toString())){
                        Customer customer = (Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        String[] parsedData = data.split("//");
                        try {
                            String logID = ControlCustomer.getInstance().purchase(customer,parsedData[0],parsedData[1],parsedData[2],parsedData[3],parsedData[4]);
                            sendMessageToClient(ServerRequest.DONE.toString() + " - " + logID + " - " + customer.getBalance());
                            Customer.rewriteFiles();
                            Account.rewriteFiles();
                            Product.rewriteFiles();
                            Seller.rewriteFiles();
                            Category.rewriteFiles();
                        } catch (Exception e) {
                            sendError(e.getMessage(),true);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLAUCTIONS.toString())){
                        String output = "NONE";
                        int i=0;
                        System.out.println("n;ojlkh" + output);
                        for (Auction auction : Auction.getAllAuctions()) {
                            if(i != 0)
                                output += " - ";
                            else
                                output = "";
                            System.out.println("hatta inja hm");
                            Product product = auction.getAuctionProduct();
                            output += auction.getAuctionId() + "&" + auction.getStartTime().toString() + "&" + auction.getEndTime().toString() + "&" + auction.getSeller().getUsername()
                                    + "&" + auction.getMaxSuggestedAmount()+product.getName() + "&" + product.getPrice() + "&" + product.getBuyersAverageScore() + "&" + product.getOrgPrice() + "&" +
                                    product.getCompany().getName() + "&" + product.getCompany().getLocation() + "&" + product.getCategory().getName() + "&" + product.getProductId() + "&"
                                    + auction.getSeller().getFirstName() + "&" + auction.getSeller().getLastName();
                            i++;
                        }
                        sendMessageToClient(output);
                        for (Auction auction : Auction.getAllAuctions()) {
                            sendImage(auction.getAuctionProduct().getImagePath(),"Product:" + auction.getAuctionProduct().getProductId());
                        }
                    }
//                    else if(request.equalsIgnoreCase(ServerRequest.GETALLFILES.toString())){
//                        String output = "NONE";
//                        int i =0;
//                        for (String s : ServerCenter.getInstance().getAllFiles().keySet()) {
//                            if(i!=0)
//                                output += " - ";
//                            else
//                                output = "";
//                            File file = ServerCenter.getInstance().getAllFiles().get(s);
//                            output += file.getName() + "&" + file.getTotalSpace() + "&" + getFileExt(file.getPath());
//                            i++;
//                        }
//                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETALLSELLERFILES.toString())){
                        String output = "NONE";
                        int i=0;
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        for (String s : Control.getInstance().getAllFiles().keySet()){
                            String[] parsed = s.split(" - ");
                            if(!parsed[0].equals(account.getUsername()))
                                continue;
                            if(i!=0)
                                output += " - ";
                            else
                                output = "";
                            File file = Control.getInstance().getAllFiles().get(s);

                            output += parsed[1] + "&" + file.getName() + "&" + file.getTotalSpace() + "&" + getFileExt(file.getPath());
                            i++;
                        }
                        sendMessageToClient(output);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.POSTUPLOADFILE.toString())){
                        if(!data.matches("\\d+")){
                            sendError("Your price format is wrong",true);
                        }else{
                            Seller seller = (Seller) ServerCenter.getInstance().getAccountFromToken(token);
                            Control.getInstance().addFile(seller.getUsername() + " - " + data,receiveFile());
                            sendError("Uploaded to our dear servers !",false);
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETACCOUNT.toString())){
                        if(!token.equalsIgnoreCase("NULL")){
                            Account account = ServerCenter.getInstance().getAccountFromToken(token);
                            sendMessageToClient(account.getUsername() + "-" + account.getType());
                        } else {
                            sendMessageToClient("guest");
                        }

                    }
                } catch (IOException e) {
//                    System.out.println("error in reading req in server");
                }
            }
        }
    }

    public static void main(String[] args) {
//        readFilesFromDatabase();
//        for (String s : Account.getAllAccounts().keySet()) {
//            System.out.println(s + "  " + Account.getAllAccounts().get(s).getPassword() + "    " + Account.getAllAccounts().get(s).getType());
//        }
//        for (String s : Company.getAllCompanies().keySet()) {
//            System.out.println("Comp " + s);
//        }
//        ((Customer)Account.getAllAccounts().get("customer")).setBalance(10000);
//            ServerSocket serverSocket = new ServerSocket(8080);
//            Socket clientSocket;
//            while (true){
//                clientSocket = serverSocket.accept();
        new Thread(new Runnable() {
            @Override
            public void run() {
                readFilesFromDatabase();
                for (String s : Account.getAllAccounts().keySet()) {
                    System.out.println(s + "  " + Account.getAllAccounts().get(s).getPassword() + "    " + Account.getAllAccounts().get(s).getType());
                }
                for (String s : Product.getAllProducts().keySet()) {
                    System.out.println(s + " " + Product.getAllProducts().get(s).getName());
                }
                try {
                    ServerSocket serverSocket = new ServerSocket(8080);
                    Socket clientSocket;
                    while (true){
                        clientSocket = serverSocket.accept();
//                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
//                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                        DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                        new HandleClient(clientSocket,dataOutputStream,dataInputStream,serverSocket,clientSocket.getInputStream(),clientSocket.getOutputStream()).start();
                    }
                } catch (IOException e) {
                    System.out.println("Error in server socket");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new ChatServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new Group_Server();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


}
}
