package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.Model.DiscountCode;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageDiscountCodes extends GraphicFather implements Initializable {
    public TableView<DiscountCode> listDiscountCodes;

    public TableColumn<DiscountCode,String> id = new TableColumn<>("DiscountID");
    public Label alertLabel;
    public TextField codeToEdit;
    public TextField codeToRemove;
    public TextField codeToView;
    private ArrayList<DiscountCode> allDiscountCodes = new ArrayList<>();

    ObservableList<DiscountCode> getDiscountCode(){
        ObservableList<DiscountCode> result =  FXCollections.observableArrayList();
        result.addAll(allDiscountCodes);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLDISCOUNTCODES);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] allDiscountCodesString = response.split(" - ");
            for (String s : allDiscountCodesString){
                String[] parsed = s.split("&");
                allDiscountCodes.add(new DiscountCode(parsed[0],parsed[1],parsed[2],parsed[3]));
            }
        }
        listDiscountCodes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        listDiscountCodes.setItems(getDiscountCode());
        listDiscountCodes.getColumns().add(id);
    }
    private boolean checkDiscountCodeExist(String id){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETDISCOUNTCODEEXISTS,id);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(response.startsWith(ServerRequest.TRUE.toString()))
            return true;
        return false;
    }

    public void viewDiscountCode(MouseEvent mouseEvent) {
        if(checkDiscountCodeExist(codeToView.getText())){
            ClientCenter.getInstance().setDiscountCodeToView(codeToView.getText());
            goToNextPage(Page.VIEWDISCOUNTCODE,mouseEvent);
        }else{
            showError(alertLabel,"This code doesn't exist!", Error.NEGATIVE);
        }
    }

    public void RemoveDiscountCode(MouseEvent mouseEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTREMOVEDISCOUNTCODE,codeToRemove.getText());
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString())){
            showError(alertLabel,"Code " + codeToRemove.getText()+" has been successfully deleted!",Error.POSITIVE);
        }else{
            showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message),Error.NEGATIVE);
        }
    }

    public void createDiscountCode(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATEDISCOUNTCODE,mouseEvent);
    }

    public void EditDiscountCode(MouseEvent mouseEvent) {
        if(checkDiscountCodeExist(codeToEdit.getText())){
            ClientCenter.getInstance().setDiscountCodeToEdit(codeToEdit.getText());
            goToNextPage(Page.EDITDISCOUNTCODE,mouseEvent);
        }else{
            showError(alertLabel,"This code doesn't exist!",Error.NEGATIVE);
        }
    }

    public void selection(MouseEvent mouseEvent) {
        DiscountCode selectedItem = listDiscountCodes.getSelectionModel().getSelectedItem();
        codeToRemove.setText(selectedItem.getDiscountId());
        codeToEdit.setText(selectedItem.getDiscountId());
        codeToView.setText(selectedItem.getDiscountId());
    }
}
