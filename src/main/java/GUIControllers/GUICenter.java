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
}
