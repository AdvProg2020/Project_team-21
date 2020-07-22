package Client.GUIControllers.Bank.BankGUIControllers;

import Bank.BankTransactionController;
import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.Main;
import Client.ServerRequest;
import Server.Controller.Control;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class WalletPageControllers extends GraphicFather {

    public TextField ChargeWalletTextField;
    public TextField WithdrawWalletTextFiled;

    public void ChangeWallet(ActionEvent actionEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.CHARGEWALLET , ChargeWalletTextField.getText());
    }

    public void WithDrawWallet(ActionEvent actionEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.WITHDRAWWALLET , WithdrawWalletTextFiled.getText());
    }
}
