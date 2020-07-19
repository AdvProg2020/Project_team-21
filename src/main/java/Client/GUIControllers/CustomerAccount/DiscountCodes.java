package Client.GUIControllers.CustomerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.Model.DiscountCode;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiscountCodes extends GraphicFather implements Initializable {
    private ArrayList<DiscountCode> discountCodes = new ArrayList<>();

    public TableView<DiscountCode> listDiscountCodes;
    public TableColumn<DiscountCode,String> id = new TableColumn<>("DiscountID");
    public TableColumn<DiscountCode,Double> percentage = new TableColumn<>("Percentage");
    public TableColumn<DiscountCode,Integer> amounts = new TableColumn<>("Allowed Usage");
    public TableColumn<DiscountCode,Double> maxAmount = new TableColumn<>("Max Disount");


    ObservableList<DiscountCode> getDiscountCode(){
        ObservableList<DiscountCode> result =  FXCollections.observableArrayList();
        result.addAll(discountCodes);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETCUSTOMERDISCOUNTCODES);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] allDiscountCodes = response.split(" - ");
            for (String discountCode : allDiscountCodes) {
                String[] parsedData = discountCode.split("&");
                discountCodes.add(new DiscountCode(parsedData[0],parsedData[1],parsedData[2],parsedData[3]));
            }
        }


        listDiscountCodes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        percentage.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));
        amounts.setCellValueFactory(new PropertyValueFactory<>("discountNumberForEachUser"));
        maxAmount.setCellValueFactory(new PropertyValueFactory<>("maxDiscountAmount"));
        listDiscountCodes.setItems(getDiscountCode());
        listDiscountCodes.getColumns().add(id);
        listDiscountCodes.getColumns().add(percentage);
        listDiscountCodes.getColumns().add(amounts);
        listDiscountCodes.getColumns().add(maxAmount);
    }
}
