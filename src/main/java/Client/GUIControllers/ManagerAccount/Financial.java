package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.ServerRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Financial extends GraphicFather implements Initializable {
    public Label alertLabel;
    public Label rnCommission;
    public Label rnLeastAmount;
    public TextField walletMin;
    public TextField commission;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETCOMMISIONANDLEASTWALLET);
        String response = ClientCenter.getInstance().readMessageFromServer();
        rnCommission.setText("Commission Now: "+ response.split("&")[0] + "%");
        rnLeastAmount.setText("Least Wallet Amount Now: " + " " + response.split("&")[1] + "$");
    }

    public void goToMoneyPage(MouseEvent mouseEvent) {
        goToNextPage(Page.MONEYPAGE,mouseEvent);
    }

    public void setLeastWalletAmount(MouseEvent mouseEvent) {
        if(!walletMin.getText().matches("\\d+.?\\d*")){
            showError(alertLabel,"Your input is invalid.", Error.NEGATIVE);
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTLEASTWALLETAMOUNT,walletMin.getText());
            showError(alertLabel,"Updated!",Error.POSITIVE);
            initialize(null,null);
        }
    }

    public void setCommission(MouseEvent mouseEvent) {
        if(!commission.getText().matches("\\d+")){
            showError(alertLabel,"Your input is invalid.", Error.NEGATIVE);
        }else if(Integer.parseInt(commission.getText()) >= 100){
            showError(alertLabel,"Commission should be under 100",Error.NEGATIVE);
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCOMMISSION,commission.getText());
            showError(alertLabel,"Updated!",Error.POSITIVE);
            initialize(null,null);
        }
    }
}
