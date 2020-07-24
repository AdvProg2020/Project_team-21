package Client;

import Client.GUIControllers.GUICenter;
import Client.GUIControllers.Page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {

    private static DataInputStream dis;
    private static DataOutputStream dos;

    //    MediaPlayer mediaPlayer;
    public void music() {
//        Media h = new Media(getClass().getResource("/Musics/back.mp3").toString());
//        mediaPlayer = new MediaPlayer(h);
//        mediaPlayer.play();
//        mediaPlayer.setOnEndOfMedia(new Runnable() {
//            public void run() {
//                mediaPlayer.seek(Duration.ZERO);
//            }
//        });
//        mediaPlayer.setVolume(GUICenter.getInstance().getMusicVolume());
//        GUICenter.getInstance().setMediaPlayer(mediaPlayer);
    }

    private static void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 8080);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ClientCenter.getInstance().setDataInputStream(dataInputStream);
            ClientCenter.getInstance().setDataOutputStream(dataOutputStream);
            ClientCenter.getInstance().setOutputStream(socket.getOutputStream());
            ClientCenter.getInstance().setInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ConnectBankClient();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        connectToServer();
        launch(args);
//        ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
//        ConsoleView.getInstance().processInput("main menu");

    }

//    private static void ConnectBankClient() throws IOException {
//        Scanner scn = new Scanner(System.in);
//        Socket bankSocket = new Socket("localhost", 8787);
//        dis = new DataInputStream(bankSocket.getInputStream());
//        dos = new DataOutputStream(bankSocket.getOutputStream());
//        Thread sendMessage = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//
//                    // read the message to deliver.
//                    String msg = scn.nextLine();
//                    try {
//                        // write on the output stream
//                        dos.writeUTF(msg);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        // readMessage thread
//        Thread readMessage = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (true) {
//                    try {
//                        // read the message sent to this client
//                        String msg = dis.readUTF();
//                        System.out.println(msg);
//
//                    } catch (IOException e) {
//
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        readMessage.start();
//        sendMessage.start();
//    }

    private static void expireAfterShut(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if(!ClientCenter.getInstance().getActiveToken().equalsIgnoreCase("NULL"))
                    ClientCenter.getInstance().sendReqToServer(ServerRequest.SIGNOUT);
                System.out.println("Running Shutdown Hook");
            }
        });

    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Idiots Market");
//        music();
        expireAfterShut();
        ClientCenter.getInstance().sendReqToServer(ServerRequest.HASMANAGER);
//        String message = ClientCenter.getInstance().getDataInputStream().readUTF();
        String message = ClientCenter.getInstance().readMessageFromServer();
        if (message.equalsIgnoreCase(ServerRequest.TRUE.toString())) {
            Parent managerMaker = FXMLLoader.load(getClass().getResource("/fxml/MakeManagerFirst.fxml"));
            scene = new Scene(managerMaker);
            stage.setScene(scene);
            GUICenter.getInstance().addSeenPage(Page.MAKEMANAGERFIRST);
            GUICenter.getInstance().setCurrentMenu(Page.MAKEMANAGERFIRST);
            GUICenter.getInstance().setCurrentScene(scene);
        } else {
            Parent mainPage = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
            stage.setMaximized(true);
            scene = new Scene(mainPage);
            stage.setScene(scene);
            GUICenter.getInstance().addSeenPage(Page.MAIN);
            GUICenter.getInstance().setCurrentMenu(Page.MAIN);
            GUICenter.getInstance().setCurrentScene(scene);
        }
        stage.show();
    }
}