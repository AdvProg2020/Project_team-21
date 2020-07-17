package Client;

import Server.Controller.ServerRequest;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class ClientCenter {
    private static ClientCenter instance;
    private String activeToken = "NULL";
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private OutputStream outputStream;
    private InputStream inputStream;
    private PrintWriter printWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

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

    public String readMessageFromServer() throws IOException {
        String result = bufferedReader.readLine();
        return result;
    }

    public boolean isTokenActivate(){
        if(!activeToken.equalsIgnoreCase("NULL"))
            return true;
        return false;
    }
    public void expireToken(){
        activeToken = "NULL";
    }

    public Image recieveImage() throws IOException {
        String ext = readMessageFromServer();
        FileOutputStream fileOutputStream = new FileOutputStream("cachedPhoto." + ext);
        byte[] b = new byte[2000000];
        dataInputStream.read(b,0,b.length);
        fileOutputStream.write(b,0,b.length);
        fileOutputStream.flush();
        File file = new File("cachedPhoto." + ext);
        Image image = new Image(file.toURI().toString());
        return image;
    }

}
