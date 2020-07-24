package Client;

import javafx.scene.image.Image;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Base64;

public class ClientCenter {
    private String bankAccountToken="";
    private static ClientCenter instance;
    private String activeToken = "NULL";
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private OutputStream outputStream;
    private InputStream inputStream;
    private PrintWriter printWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private String productToView;
    private String productToViewBuyers;
    private String productToEdit;
    private String offToEdit;
    private String categoryToEdit;
    private String userToView;
    private String sellerToAddCompany;
    private String discountCodeToView;
    private String discountCodeToEdit;
    private String requestToView;
    private int remainingAttempts;
    private long startedLoginError;

    public static ClientCenter getInstance() {
        if(instance == null)
            instance = new ClientCenter();
        return instance;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        printWriter = new PrintWriter(outputStream);
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
    }

    public String getSellerToAddCompany() {
        return sellerToAddCompany;
    }

    public void setSellerToAddCompany(String sellerToAddCompany) {
        this.sellerToAddCompany = sellerToAddCompany;
    }

    public void setDiscountCodeToView(String discountCodeToView) {
        this.discountCodeToView = discountCodeToView;
    }

    public void setDiscountCodeToEdit(String discountCodeToEdit) {
        this.discountCodeToEdit = discountCodeToEdit;
    }

    public String getDiscountCodeToEdit() {
        return discountCodeToEdit;
    }

    public String getRequestToView() {
        return requestToView;
    }

    public void setRequestToView(String requestToView) {
        this.requestToView = requestToView;
    }

    public String getDiscountCodeToView() {
        return discountCodeToView;
    }

    public String getOffToEdit() {
        return offToEdit;
    }

    public void setCategoryToEdit(String categoryToEdit) {
        this.categoryToEdit = categoryToEdit;
    }

    public String getUserToView() {
        return userToView;
    }

    public void setUserToView(String userToView) {
        this.userToView = userToView;
    }

    public String getCategoryToEdit() {
        return categoryToEdit;
    }

    public void setOffToEdit(String offToEdit) {
        this.offToEdit = offToEdit;
    }

    public void setProductToEdit(String productToEdit) {
        this.productToEdit = productToEdit;
    }

    public String getProductToEdit() {
        return productToEdit;
    }

    public void setProductToView(String productToView) {
        this.productToView = productToView;
    }

    public String getProductToView() {
        return productToView;
    }

    public void setProductToViewBuyers(String productToViewBuyers) {
        this.productToViewBuyers = productToViewBuyers;
    }

    public String getProductToViewBuyers() {
        return productToViewBuyers;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setActiveToken(String activeToken) {
        this.activeToken = activeToken;
    }

    public String getActiveToken() {
        return activeToken;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public String getBankAccountToken() {
        return bankAccountToken;
    }

    public void setBankAccountToken(String bankAccountToken) {
        this.bankAccountToken = bankAccountToken;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void sendReqToServer(ServerRequest request,String data){
        printWriter.println(getActiveToken() + " - " + request + " - " + data);
        printWriter.flush();
    }

    public void sendReqToServer(ServerRequest request){
        printWriter.println(getActiveToken() + " - " + request + " - " + "NULL");
        printWriter.flush();
    }

    public String readMessageFromServer(){
        String result = null;
        try {
            result = bufferedReader.readLine();
        } catch (IOException e) {
        }
        return result;
    }

    public String getMessageFromError(String message){
        String[] parsed = message.split("&");
        return parsed[1];
    }

    public boolean isTokenActivate(){
        if(!activeToken.equalsIgnoreCase("NULL"))
            return true;
        return false;
    }
    public void expireToken(){
        activeToken = "NULL";
    }

    private byte[] decodeImage(String imageDataString) {
        return Base64.getDecoder().decode(imageDataString);
    }

    public Image recieveImage() throws IOException {
        System.out.println("File Received!");
        String message = readMessageFromServer();
        JSONObject obj1 = (JSONObject) JSONValue.parse(message);
        String name = obj1.get("filename").toString();
        String image = obj1.get("image").toString();
        byte[] imageByteArray = decodeImage(image);
        FileOutputStream imageOutFile = new FileOutputStream("cachedPhotos/" + name);
        imageOutFile.write(imageByteArray);
        imageOutFile.flush();
        File file = new File("cachedPhotos/" + name);
        Image photo = new Image(file.toURI().toString());
        file.delete();
        System.out.println("Image Successfully Manipulated!");
        return photo;
    }

    public void downloadFile() throws IOException {
        System.out.println("File Received!");
        String message = readMessageFromServer();
        JSONObject obj1 = (JSONObject) JSONValue.parse(message);
        String name = obj1.get("filename").toString();
        String image = obj1.get("image").toString();
        byte[] imageByteArray = decodeImage(image);
        String home = System.getProperty("user.home");
        FileOutputStream imageOutFile = new FileOutputStream(home+"/Downloads/" + name);
        imageOutFile.write(imageByteArray);
        imageOutFile.flush();
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

    public void sendImage(String path) throws IOException {
        File file = new File(path);
        FileInputStream imageInFile = new FileInputStream(file);
        byte imageData[] = new byte[(int) file.length()];
        imageInFile.read(imageData);
        String imageDataString = encodeImage(imageData);
        System.out.println("Image Successfully Manipulated!");
        JSONObject obj = new JSONObject();
        obj.put("filename","chert" + "." +getFileExt(path));
        obj.put("image",imageDataString);
        printWriter.println(obj.toJSONString());
        printWriter.flush();
        System.out.println("File Sent!");
    }

    public void sendFile(String path,String name) throws IOException {
        File file = new File(path);
        FileInputStream imageInFile = new FileInputStream(file);
        byte imageData[] = new byte[(int) file.length()];
        imageInFile.read(imageData);
        String imageDataString = encodeImage(imageData);
        System.out.println("Image Successfully Manipulated!");
        JSONObject obj = new JSONObject();
        obj.put("filename",name);
        obj.put("file",imageDataString);
        printWriter.println(obj.toJSONString());
        printWriter.flush();
        System.out.println("File Sent!");
    }

    public Image getImageExt(String ext){
        Image result = null;
        if(ext.equalsIgnoreCase("pdf")){
            URL res = getClass().getClassLoader().getResource("images/pdf.png");
            try {
                result = new Image(res.toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else if(ext.matches("(?i)docx|doc")){
            URL res = getClass().getClassLoader().getResource("images/doc.png");
            try {
                result = new Image(res.toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else if(ext.matches("(?i)png|jpg|jpeg")){
            URL res = getClass().getClassLoader().getResource("images/image.png");
            try {
                result = new Image(res.toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else if(ext.matches("(?i)mp3|wav|m4a")){
            URL res = getClass().getClassLoader().getResource("images/music.png");
            try {
                result = new Image(res.toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else if(ext.matches("(?i)mp4|mkv|webm")){
            URL res = getClass().getClassLoader().getResource("images/video.png");
            try {
                result = new Image(res.toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }else{
                URL res = getClass().getClassLoader().getResource("images/file.png");
                try {
                    result = new Image(res.toURI().toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setStartedLoginError(long startedLoginError) {
        this.startedLoginError = startedLoginError;
    }

    public long getStartedLoginError() {
        return startedLoginError;
    }
}
