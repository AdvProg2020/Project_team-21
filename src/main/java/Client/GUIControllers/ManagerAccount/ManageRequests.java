package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.Model.Request;
import Client.ServerRequest;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageRequests extends GraphicFather implements Initializable {
    public TextField requestToView;
    public TextField requestToAccept;
    public TextField requestToDecline;
    public TableView<Request> listRequests;
    public Label alertLabel;
    private ArrayList<Request> allRequests = new ArrayList<>();

    public TableColumn<Request,String> type = new TableColumn<>("Type");
    public TableColumn<Request,String> id = new TableColumn<>("RequestId");
    public TableColumn<Request,String> provider = new TableColumn<>("Provider ");


    ObservableList<Request> getRequests(){
        ObservableList<Request> result =  FXCollections.observableArrayList();
        result.addAll(allRequests);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLREQUESTS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] reqs = response.split(" - ");
            for (String req : reqs) {
                String[] parseData = req.split("&");
                allRequests.add(new Request(parseData[0],parseData[1],parseData[2]));
            }
        }
        listRequests.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        id.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        provider.setCellValueFactory(new PropertyValueFactory<>("provider"));
        listRequests.setItems(getRequests());
        listRequests.getColumns().add(type);
        listRequests.getColumns().add(id);
        listRequests.getColumns().add(provider);
    }

    public void acceptReq(MouseEvent mouseEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTACCEPTREQUEST,requestToAccept.getText());
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString()))
            showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message), Error.POSITIVE);
        else
            showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message),Error.NEGATIVE);
    }

    public void declineReq(MouseEvent mouseEvent) {

        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTDECLINEREQUEST,requestToDecline.getText());
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString()))
            showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message), Error.POSITIVE);
        else
            showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message),Error.NEGATIVE);
    }

    public void viewReq(MouseEvent mouseEvent) {
        String requestId = requestToView.getText();
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETCHECKREQUESTEXISTS,requestId);
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.TRUE.toString())){
            ClientCenter.getInstance().setRequestToView(requestToView.getText());
            goToNextPage(Page.VIEWREQUEST,mouseEvent);
        }else {
            showError(alertLabel,"This request ID doesn't exist!",Error.NEGATIVE);
        }
    }


    public void selection(MouseEvent mouseEvent) {
        Request selectedItem = listRequests.getSelectionModel().getSelectedItem();
        requestToView.setText(selectedItem.getRequestId());
        requestToAccept.setText(selectedItem.getRequestId());
        requestToDecline.setText(selectedItem.getRequestId());
    }
}
