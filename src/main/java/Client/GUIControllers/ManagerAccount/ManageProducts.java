package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
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
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageProducts extends GraphicFather implements Initializable {
    public TextField productToRemove;
    public TableView<Product> listProducts;
    public TableColumn<Product,String> name = new TableColumn<>("Name");
    public TableColumn<Product,String> id = new TableColumn<>("ProductId");
    public Label alertLabel;
    private ArrayList<Product> allProducts = new ArrayList<>();

    ObservableList<Product> getProducts(){
        ObservableList<Product> result =  FXCollections.observableArrayList();
        result.addAll(allProducts);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLPRODUCTS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] products = response.split(" - ");
            for (String product : products) {
                String[] parsedData = product.split("&");
                String name = parsedData[0];
                String id = parsedData[1];
                allProducts.add(new Product(name,0,0,0,"","","",null,id));
            }
        }
        listProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        listProducts.setItems(getProducts());
        listProducts.getColumns().add(name);
        listProducts.getColumns().add(id);
    }

    public void removeProduct(MouseEvent mouseEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTREMOVEPRODUCT,productToRemove.getText());
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString())){
            showError(alertLabel,"Product " + productToRemove.getText() + " has been successfully deleted!",Error.POSITIVE);
        }else{
            showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message), Error.NEGATIVE);
        }
    }

    public void selection(MouseEvent mouseEvent) {
        Product selectedItem = listProducts.getSelectionModel().getSelectedItem();
        productToRemove.setText(selectedItem.getProductId());
    }
}
