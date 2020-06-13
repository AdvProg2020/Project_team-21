package GUIControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;

public class GUICenter {
   private static GUICenter instance;
    private ArrayList<Scene> seenPages;
    private Scene currentMenu;
    private Scene LandingPageAfterSigninOrSignup;
    private GUICenter()
    {
        seenPages = new ArrayList<>();

    }
    public static GUICenter getInstance() {
        if(instance == null)
        {
            instance = new GUICenter();
        }
        return instance;
    }

    public ArrayList<Scene> getSeenPages() {
        return seenPages;
    }
    public void addSeenPage(Scene scene){
        seenPages.add(scene);
    }

    public Scene getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Scene currentMenu) {
        this.currentMenu = currentMenu;
    }

    public Scene getLandingPageAfterSigninOrSignup() {
        return LandingPageAfterSigninOrSignup;
    }

    public void setLandingPageAfterSigninOrSignup(Scene landingPageAfterSigninOrSignup) {
        LandingPageAfterSigninOrSignup = landingPageAfterSigninOrSignup;
    }
}
