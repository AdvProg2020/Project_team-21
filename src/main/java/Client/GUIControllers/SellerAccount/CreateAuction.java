package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.Model.Product;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateAuction extends GraphicFather implements Initializable {
    public Label alertLabel;
    public TableView<Product> listProducts;
    public TextField productId;
    public ComboBox<Integer> yearMenu;
    public ComboBox<Integer> monthMenu;
    public ComboBox<Integer> dayMenu;
    public ComboBox<Integer> hourMenu;
    public ComboBox<Integer> minuteMenu;
    private ArrayList<Product> allProducts = new ArrayList<>();

    ObservableList<Product> getProducts(){
        ObservableList<Product> result =  FXCollections.observableArrayList();
        result.addAll(allProducts);
        return result;
    }

    public TableColumn<Product,String> name = new TableColumn<>("Name");
    public TableColumn<Product,String> id = new TableColumn<>("ProductId");
    private void fillProductsTable(){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLERPRODUCTS);
        try{
            String response = ClientCenter.getInstance().readMessageFromServer();
            if(!response.equalsIgnoreCase("NONE")){
                ArrayList<Image> allImages = new ArrayList<>();
                String[] allProductsString = response.split(" - ");
                for (int i = 0; i < allProductsString.length; i++) {
                    allImages.add(ClientCenter.getInstance().recieveImage());
                }

                for (int i = 0; i < allProductsString.length ; i++) {
                    String[] productData = allProductsString[i].split("&");
                    allProducts.add(new Product(productData[0],Double.parseDouble(productData[1]),Double.parseDouble(productData[3]),
                            Double.parseDouble(productData[2]),productData[4],productData[5],productData[6],allImages.get(i),productData[7]));
                }
            }
        }catch (IOException e){
        }
        listProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        listProducts.setItems(getProducts());
        listProducts.getColumns().add(name);
        listProducts.getColumns().add(id);
    }

    private void fillComboBox(){
        ArrayList<Integer> menuShow = new ArrayList<>();
        for (int i = LocalDate.now().getYear(); i <= LocalDate.now().getYear() + 5 ; i++) {
            menuShow.add(i);
        }
        yearMenu.setItems(FXCollections.observableArrayList(menuShow));
        menuShow.clear();
        for (int i = 1; i <= 12; i++) {
            menuShow.add(i);
        }
        monthMenu.setItems(FXCollections.observableArrayList(menuShow));
        menuShow.clear();
        for (int i = 1; i <= 31; i++) {
            menuShow.add(i);
        }
        dayMenu.setItems(FXCollections.observableArrayList(menuShow));
        menuShow.clear();
        for (int i = 0; i <= 23; i++) {
            menuShow.add(i);
        }
        hourMenu.setItems(FXCollections.observableArrayList(menuShow));
        menuShow.clear();
        for (int i = 0; i <= 60; i++) {
            menuShow.add(i);
        }
        minuteMenu.setItems(FXCollections.observableArrayList(menuShow));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillComboBox();
        fillProductsTable();
    }

    public void done(MouseEvent mouseEvent) {
        System.out.println();
        System.out.println(monthMenu.getSelectionModel().getSelectedItem());
        if(yearMenu.getSelectionModel().getSelectedItem() == null ||monthMenu.getSelectionModel().getSelectedItem() == null||
                dayMenu.getSelectionModel().getSelectedItem() == null||hourMenu.getSelectionModel().getSelectedItem() == null||
                minuteMenu.getSelectionModel().getSelectedItem() == null||productId.getText().isEmpty()){
            showError(alertLabel,"You should fill all the fields.", Error.NEGATIVE);
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATEAUCTION,yearMenu.getSelectionModel().getSelectedItem() + "&" +
                    monthMenu.getSelectionModel().getSelectedItem() + "&" + dayMenu.getSelectionModel().getSelectedItem() + "&" +hourMenu.getSelectionModel().getSelectedItem()
            + "&" + minuteMenu.getSelectionModel().getSelectedItem() + "&" + productId.getText());
            String response = ClientCenter.getInstance().readMessageFromServer();
            if(response.startsWith(ServerRequest.DONE.toString()))
                showError(alertLabel,ClientCenter.getInstance().getMessageFromError(response),Error.POSITIVE);
            else
                showError(alertLabel,ClientCenter.getInstance().getMessageFromError(response),Error.NEGATIVE);
        }
    }


    public void selection(MouseEvent mouseEvent) {
        Product selectedItem = listProducts.getSelectionModel().getSelectedItem();
        productId.setText(selectedItem.getProductId());
    }

}
