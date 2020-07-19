package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class EditProduct extends GraphicFather {
    public TextField name;
    public TextField price;
    public Label alertLabel;
    public Label okLabel;
    private ArrayList<String> oks = new ArrayList<>();
    private ArrayList<String> errors = new ArrayList<>();

    public void makeChange(MouseEvent mouseEvent) {
        String productID = ClientCenter.getInstance().getProductToEdit();
        ArrayList<String> reqIDs = new ArrayList<>();
        if(!name.getText().isEmpty()){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTEDITPRODUCTREQ,productID + "//name//" + name.getText());
            String message = ClientCenter.getInstance().readMessageFromServer();
            if(message.startsWith(ServerRequest.DONE.toString())){
                String reqID = ClientCenter.getInstance().getMessageFromError(message);
                System.out.println("edit name sent");
                reqIDs.add(reqID);
                oks.add("Name");
            }else{
                System.out.println("name rid");
                errors.add("Name: " + ClientCenter.getInstance().getMessageFromError(message));
            }
        }
        if(!price.getText().isEmpty()){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTEDITPRODUCTREQ,productID + "//price//" + price.getText());
            String message = ClientCenter.getInstance().readMessageFromServer();
            if(message.startsWith(ServerRequest.DONE.toString())){
                String reqID = ClientCenter.getInstance().getMessageFromError(message);
                System.out.println("edit price sent");
                reqIDs.add(reqID);
                oks.add("Price");
            }else{
                System.out.println("price rid");
                errors.add("Price: " + ClientCenter.getInstance().getMessageFromError(message));
            }
        }
        String ok="";
        String error="";
        for (String s : oks) {
            ok += s +" ";
        }
        for (String s : errors) {
            error += s+" ";
        }
        showError(alertLabel,"Fields " + error + "had problems", Error.NEGATIVE);
        showError(okLabel,"Fields " + ok + "had been successfully changed with these Request IDs:",Error.POSITIVE);
        for (String reqID : reqIDs) {
            okLabel.setText(okLabel.getText() + "\n" + reqID);
        }
    }

}
