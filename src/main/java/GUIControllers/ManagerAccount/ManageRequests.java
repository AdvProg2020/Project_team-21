package GUIControllers.ManagerAccount;

import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Seller;
import Model.Off;
import Model.Product;
import Model.Request.*;
import View.ConsoleView;
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

public class ManageRequests extends GraphicFather implements Initializable {
    public TextField requestToView;
    public TextField requestToAccept;
    public TextField requestToDecline;
    public TableView<Request> listRequests;
    public Label alertLabel;

    public TableColumn<Request,String> type = new TableColumn<>("Type");
    public TableColumn<Request,String> id = new TableColumn<>("RequestId");
    public TableColumn<Request,String> provider = new TableColumn<>("Provider ");


    ObservableList<Request> getRequests(){
        ObservableList<Request> result =  FXCollections.observableArrayList();
        for (String s : Request.getAllRequests().keySet()) {
            result.add(Request.getAllRequests().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listRequests.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        id.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        provider.setCellValueFactory(new PropertyValueFactory<>("providerUsername"));
        listRequests.setItems(getRequests());
        listRequests.getColumns().add(type);
        listRequests.getColumns().add(id);
        listRequests.getColumns().add(provider);
    }

    public void acceptReq(MouseEvent mouseEvent) {
        String requestId = requestToAccept.getText();
        if(ControlManager.getInstance().checkRequestIdExistance(requestId))
        {
            if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.ADD))
            {
                showError(alertLabel,"It has been successfully added!", Error.POSITIVE);
            }
            else if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.DELETE))
            {
                showError(alertLabel,"It has been successfully deleted!", Error.POSITIVE);
            }
            else if(Request.getAllRequests().get(requestId).getRequestType().equals(RequestType.EDIT))
            {
                showError(alertLabel,"It has been successfully edited!", Error.POSITIVE);
            }
            Request.getAllRequests().get(requestId).acceptReq(requestId);
        }
        else
        {
            showError(alertLabel,"This request ID doesn't exist!",Error.NEGATIVE);
        }
    }

    public void declineReq(MouseEvent mouseEvent) {
        String requestId = requestToDecline.getText();
        if(ControlManager.getInstance().checkRequestIdExistance(requestId))
        {
            Request.getAllRequests().get(requestId).declineReq(requestId);
            showError(alertLabel,"It has been declined successfully!",Error.POSITIVE);
        }
        else
        {
            showError(alertLabel,"This request ID doesn't exist!",Error.NEGATIVE);
        }
    }

    public void viewReq(MouseEvent mouseEvent) {
        String requestId = requestToView.getText();
        if(ControlManager.getInstance().checkRequestIdExistance(requestId)){
            ControlManager.getInstance().setRequestToView(requestToView.getText());
            goToNextPage(Page.VIEWREQUEST,mouseEvent);
        }
        else{
            showError(alertLabel,"This request ID doesn't exist!",Error.NEGATIVE);
        }
    }


}
