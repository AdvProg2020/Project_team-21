package Client.GUIControllers.Bank.BankGUIControllers;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
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

    public TextField user;
    public PasswordField password;
    public TextField money;

    public void ChangeWallet(ActionEvent actionEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETACCOUNT);
        String res = ClientCenter.getInstance().readMessageFromServer();
        String[] splitted = res.split("-");
        Account user = Account.getAccountFromUserName(splitted[0]);
//        if (user instanceof Customer){
//            Control.getInstance().customerWalletCharge((Customer) user , Double.parseDouble(ChargeWalletTextField.getText()));
//        }else if (user instanceof Seller){
//            Control.getInstance().sellerWalletCharge((Seller) user ,Double.parseDouble(ChargeWalletTextField.getText()) );
//        }

//        ArrayList<String> input = new ArrayList<>();
//        input.add(user.getText());
//        input.add(password.getText());
//        input.add(money.getText());
//        RequestForBankServer requestForServer = new RequestForBankServer("BankTransactionsController", "increaseCustomerCredit", getToken(), input);
//        String response = connectToServer(requestForServer);
////        if (response.equals("done successfully"))
//////            SuccessPageFxController.showPage("successfully done", "credit increased successfully");
//////        else {
////////            ErrorPageFxController.showPage("error happened", response);
//////        }
//
//    }
    }
}
