package Client.GUIControllers.Bank.BankGUIControllers;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import Server.Controller.Control;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class WalletPageControllers extends GraphicFather {
    public TextField ChargeWalletTextField;

    public void ChangeWallet(ActionEvent actionEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETACCOUNT);
        String res = ClientCenter.getInstance().readMessageFromServer();
        String[] splitted = res.split("-");
        Account user = Account.getAccountFromUserName(splitted[0]);
        if (user instanceof Customer){
            Control.getInstance().customerWalletCharge((Customer) user , Double.parseDouble(ChargeWalletTextField.getText()));
        }else if (user instanceof Seller){
            Control.getInstance().sellerWalletCharge((Seller) user ,Double.parseDouble(ChargeWalletTextField.getText()) );
        }

    }
}
