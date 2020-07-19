package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.OffsPagePrime;
import Client.GUIControllers.Page;
import Client.Model.Off;
import Client.Model.Product;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class OFFs extends GraphicFather implements Initializable {
    public TextField offToView;
    public TableView<Off> listOFFs;
    public TextField offToEdit;
    public Label alertLabel;
    public TableColumn<Off,String> id = new TableColumn<>("OFF ID");
    private ArrayList<Off> offsForHere = new ArrayList<>();

    ObservableList<Off> getOffs(){
        ObservableList<Off> result =  FXCollections.observableArrayList();
        result.addAll(offsForHere);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLEROFFS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if (!response.equalsIgnoreCase("NONE")) {
            String[] allOffs = response.split(" - ");
            for (String allOff : allOffs) {
                String[] parsedData = allOff.split("&");
                Off off = new Off(Double.parseDouble(parsedData[0]),parsedData[1],parsedData[2],parsedData[3]);
                ClientCenter.getInstance().sendReqToServer(ServerRequest.GETOFFSPRODUCTS,parsedData[3]);
                String message = ClientCenter.getInstance().readMessageFromServer();
                if(!message.equalsIgnoreCase("NONE")){
                    String[] allProducts = message.split(" - ");
                    for (String product : allProducts){
                        Image image = null;
                        try {
                            image = ClientCenter.getInstance().recieveImage();
                        } catch (IOException e) {
                        }
                        String[] productData = product.split("&");
                        off.addProduct(new Product(productData[0],Double.parseDouble(productData[1]),Double.parseDouble(productData[3]),
                                Double.parseDouble(productData[2]),productData[4],productData[5],productData[6],image,productData[7]));
                    }
                }
                offsForHere.add(off);
            }
        }
        listOFFs.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setCellValueFactory(new PropertyValueFactory<>("offId"));
        listOFFs.setItems(getOffs());
        listOFFs.getColumns().add(id);
    }

    private HashMap<String,Off> getOffsMap(){
        HashMap<String,Off> result = new HashMap<>();
        for (Off off : offsForHere) {
            result.put(off.getOffId(),off);
        }
        return result;
    }

    private boolean checkSellerGotOff(String offId){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLERGOTOFF,offId);
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.TRUE.toString()))
            return true;
        return false;
    }

    public void viewOFF(MouseEvent mouseEvent) {
        if(checkSellerGotOff(offToView.getText())){
            Off off = getOffsMap().get(offToView.getText());
            OffsPagePrime.setProducts(off.getProductsList());
            OffsPagePrime.setOff(off);
            goToNextPage(Page.OFFSPRIMEPAGE,mouseEvent);
        }else {
            showError(alertLabel,"This OFF does not exist!", Error.NEGATIVE);
        }
    }

    public void editOFF(MouseEvent mouseEvent) {
        if(checkSellerGotOff(offToEdit.getText())){
            ClientCenter.getInstance().setOffToEdit(offToEdit.getText());
            goToNextPage(Page.EDITOFF,mouseEvent);
        }else {
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
