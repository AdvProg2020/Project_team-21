package GUIControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class GUICenter {
   private static GUICenter instance;

    public static GUICenter getInstance() {
        if(instance == null)
        {
            instance = new GUICenter();
        }
        return instance;
    }
    Scene signinScene;

    public Scene getSigninScene() throws IOException {
        if(signinScene == null)
        {
            Parent template = FXMLLoader.load(getClass().getResource("/test.fxml"));
            signinScene = new Scene(template, 800, 800);
        }
        return signinScene;
    }
}
