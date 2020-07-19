package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewRequest extends GraphicFather implements Initializable {
    public Label info;
    public Label field1Info;
    public Label field2Info;
    public Label field3Info;
    public Label field4Info;
    public Label field5Info;
    public Label field6Info;
    public Label field1;
    public Label field2;
    public Label field3;
    public Label field4;
    public Label field5;
    public Label field6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            String requestId = ClientCenter.getInstance().getRequestToView();
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETREQUESTINFOS,requestId);
            String response = ClientCenter.getInstance().readMessageFromServer();
            String[] parsedData = response.split(" - ");
            info.setText(parsedData[0]);
            field1.setText(parsedData[1]);
            field2.setText(parsedData[2]);
            field3.setText(parsedData[3]);
            field4.setText(parsedData[4]);
            field5.setText(parsedData[5]);
            field6.setText(parsedData[6]);
            field1Info.setText(parsedData[7]);
            field2Info.setText(parsedData[8]);
            field3Info.setText(parsedData[9]);
            field4Info.setText(parsedData[10]);
            field5Info.setText(parsedData[11]);
            field6Info.setText(parsedData[12]);
    }
}
