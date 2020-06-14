package GUIControllers.ManagerAccount;

import GUIControllers.GraphicFather;
import Model.Product;
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

public class ManageRequests extends GraphicFather implements Initializable {
    public TextField requestToView;
    public TextField requestToAccept;
    public TextField requestToDecline;
    public Label AlertLabel;
    public TableView<Request> listRequests;

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
    }

    public void viewReq(MouseEvent mouseEvent) {
    }

    public void declineReq(MouseEvent mouseEvent) {
    }
}
