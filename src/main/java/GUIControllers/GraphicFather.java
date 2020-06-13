package GUIControllers;

import View.UserLoginUI;
import javafx.scene.Scene;

import java.util.ArrayList;

public class GraphicFather {
    Scene currentMenu = GUICenter.getInstance().getCurrentMenu();
    ArrayList<Scene> seenPages = GUICenter.getInstance().getSeenPages();
    public void goBack()
    {
        if(currentMenu.equals(UserLoginUI.getInstance()))
        {
            currentMenu = GUICenter.getInstance().getLandingPageAfterSigninOrSignup();
        }
        else {
            if(seenPages.size()>1)
                currentMenu = seenPages.get(seenPages.size()-2);
        }
        seenPages.remove(seenPages.size()-1);
    }
    public void goToNextPage(Scene menu)
    {
        seenPages.add(menu);
        currentMenu = menu;
    }
}
