package GUIControllers.SellerAccount;

import Controller.Control;
import Controller.ControlSeller;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Seller;
import Model.Off;
import Model.Product;
import Model.Request.OffRequest;
import Model.Request.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OFFs extends GraphicFather implements Initializable {
    public TextField offToView;
    public TableView<Off> listOFFs;
    public TextField offToEdit;
    public Label alertLabel;
    public TableColumn<Off,String> id = new TableColumn<>("OFF ID");
    private Seller seller = (Seller)Control.getInstance().getUser();

    ObservableList<Off> getOffs(){
        ObservableList<Off> result =  FXCollections.observableArrayList();
        result.addAll(seller.getAllOffs());
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOFFs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setCellValueFactory(new PropertyValueFactory<>("offId"));
        listOFFs.setItems(getOffs());
        listOFFs.getColumns().add(id);
    }

    public void viewOFF(MouseEvent mouseEvent) {
        if(ControlSeller.getInstance().checkOffExistance(offToView.getText()) && ControlSeller.getInstance().checkSellerGotOff(offToView.getText(),seller)){
            ControlSeller.getInstance().setOffToView(offToView.getText());
            goToNextPage(Page.VIEWOFF,mouseEvent);
        }
        else {
            showError(alertLabel,"This OFF does not exist!", Error.NEGATIVE);
        }
    }

    public void editOFF(MouseEvent mouseEvent) {
        if(ControlSeller.getInstance().checkOffExistance(offToView.getText()) && ControlSeller.getInstance().checkSellerGotOff(offToView.getText(),seller)) {
            ControlSeller.getInstance().setOffToEdit(offToEdit.getText());
            goToNextPage(Page.EDITOFF,mouseEvent);
            OffRequest.rewriteFiles();
            Off.rewriteFiles();
        }
        else {
            showError(alertLabel,"This OFF does not exist!", Error.NEGATIVE);
        }

    }

    public void createOFF(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATEOFF,mouseEvent);
    }

    public void selection(MouseEvent mouseEvent) {
        Off selectedItem = listOFFs.getSelectionModel().getSelectedItem();
        offToView.setText(selectedItem.getOffId());
        offToEdit.setText(selectedItem.getOffId());
    }
}
