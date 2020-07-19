package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class EditCategory extends GraphicFather {
    public Label alertLabel;
    public TextField newName;
    public TextField productsToRemove;
    public TextField productsToAdd;
    public Label okLabel;

    public void makeChange(MouseEvent mouseEvent) {
        String editCategory = ClientCenter.getInstance().getCategoryToEdit();
        String output = "";

        ArrayList<String> oks = new ArrayList<>();
        ArrayList<String> errors = new ArrayList<>();
        if(!newName.getText().isEmpty()){
            output += "name&" + newName.getText()+"&" + editCategory;
        }
        if(!productsToAdd.getText().isEmpty()){
            if(!output.isEmpty())
                output += "//";
            output += "addproduct&" + productsToAdd.getText() +"&" + editCategory;
        }
        if(!productsToRemove.getText().isEmpty()){
            if(!output.isEmpty())
                output += "//";
            output += "removeproduct&" + productsToRemove.getText()+"&" + editCategory;
        }
        if(!output.isEmpty()){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEEDITCATEGORY,output);
            String response = ClientCenter.getInstance().readMessageFromServer();
            String[] parsed = response.split(" - ");
            String[] oksString = parsed[0].split("&");
            String[] errorsString = parsed[1].split("&");
            for (String s : oksString) {
                oks.add(s);
            }
            for (String s : errorsString) {
                errors.add(s);
            }
            String ok = "";
            String error = "";
            for (String s : errors) {
                error += s+ " ";
            }
            for (String s : oks) {
                ok += s+ " ";
            }
            showError(alertLabel,"Fields: " + error + " had problems", Error.NEGATIVE);
            showError(okLabel,"Fields: " + ok + " had been successfully changed",Error.POSITIVE);
        }
    }
}
