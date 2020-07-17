package Client.GUIControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Settings extends GraphicFather implements Initializable {

    public Slider buttonVolume;
    public Slider musicVolume;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicVolume.setValue(GUICenter.getInstance().getMusicVolume() * 100);
        buttonVolume.setValue(GUICenter.getInstance().getClickVolume() * 100);


    }

    public void muteButton(MouseEvent mouseEvent) {
        buttonVolume.setValue(0);
        GUICenter.getInstance().setClickVolume(0);
    }

    public void muteMusic(MouseEvent mouseEvent) {
        musicVolume.setValue(0);
        GUICenter.getInstance().setMusicVolume(0);
        GUICenter.getInstance().getMediaPlayer().setVolume(0);
    }

    public void changeButtonVolume(MouseEvent mouseEvent) {
        GUICenter.getInstance().setClickVolume(buttonVolume.getValue() / 100);
    }

    public void changeMusicVolume(MouseEvent mouseEvent) {
        GUICenter.getInstance().setMusicVolume(musicVolume.getValue() / 100);
        GUICenter.getInstance().getMediaPlayer().setVolume(musicVolume.getValue() / 100);
    }
}
