package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.*;
import Client.GUIControllers.Error;
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
import java.util.ResourceBundle;

public class Products extends GraphicFather implements Initializable {
    public TextField productToRemove;
    public TextField productToEdit;
    public TextField productToBuyers;
    public TableView<Product> listProducts;
    public TextField productToView;
    public Label alertLabel;
    public TextField productToAdd;
    private ArrayList<Product> allProducts = new ArrayList<>();

    ObservableList<Product> getProducts(){
        ObservableList<Product> result =  FXCollections.observableArrayList();
        result.addAll(allProducts);
        return result;
    }

    public TableColumn<Product,String> name = new TableColumn<>("Name");
    public TableColumn<Product,String> id = new TableColumn<>("ProductId");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void addProduct(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATEPRODUCT,mouseEvent);
    }

    public void removeProduct(MouseEvent mouseEvent) {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTREMOVEPRODUCTREQ,productToRemove.getText());
            String messageFromServer = ClientCenter.getInstance().readMessageFromServer();
            if(messageFromServer.startsWith(ServerRequest.DONE.toString()))
                showError(alertLabel,"Your request with ID " + ClientCenter.getInstance().getMessageFromError(messageFromServer) + " has been sent!",Error.POSITIVE);
            else
                showError(alertLabel,ClientCenter.getInstance().getMessageFromError(messageFromServer),Error.NEGATIVE);
    }

    private boolean sellerGotProduct(String productId){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLERGOTPRODUCT,productId);
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.TRUE.toString()))
            return true;
        return false;
    }


    public void editProduct(MouseEvent mouseEvent) {
        if(sellerGotProduct(productToEdit.getText())){
            ClientCenter.getInstance().setProductToEdit(productToEdit.getText());
            goToNextPage(Page.EDITPRODUCT,mouseEvent);
        }else{
            showError(alertLabel,"You don't have this product!",Error.NEGATIVE);
        }
    }

    public void viewBuyers(MouseEvent mouseEvent) {
        if(sellerGotProduct(productToBuyers.getText())){
            ClientCenter.getInstance().setProductToViewBuyers(productToBuyers.getText());
            goToNextPage(Page.VIEWBUYERSPRODUCT,mouseEvent);
        }else{
            showError(alertLabel,"You don't have this product!",Error.NEGATIVE);
        }
    }

    public void viewProduct(MouseEvent mouseEvent) {
        if(sellerGotProduct(productToView.getText())){
            ClientCenter.getInstance().setProductToView(productToView.getText());
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETPRODUCT,productToView.getText());
            String response = ClientCenter.getInstance().readMessageFromServer();
            Image productImage = null;
            try {
                productImage = ClientCenter.getInstance().recieveImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] productData = response.split("&");
            Product product = new Product(productData[0],Double.parseDouble(productData[1]),Double.parseDouble(productData[3]),
                    Double.parseDouble(productData[2]),productData[4],productData[5],productData[6],productImage,productData[7]);
            ProductPage.setProduct(product);
            goToNextPage(Page.PRODUCTPAGE,mouseEvent);
        }else{
            showError(alertLabel,"You don't have this product!",Error.NEGATIVE);
        }
    }

    public void addProductToList(MouseEvent mouseEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTADDPRODUCTTOSELLERLIST,productToAdd.getText());
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString())){
            String reqID = ClientCenter.getInstance().getMessageFromError(message);
            showError(alertLabel,"Your request with id " + reqID + " has been sent!",Error.POSITIVE);
        }else{
            String error = ClientCenter.getInstance().getMessageFromError(message);
            showError(alertLabel,error,Error.NEGATIVE);
        }
    }

    public void selection(MouseEvent mouseEvent) {
        Product selectedItem = listProducts.getSelectionModel().getSelectedItem();
        productToRemove.setText(selectedItem.getProductId());
        productToEdit.setText(selectedItem.getProductId());
        productToBuyers.setText(selectedItem.getProductId());
        productToView.setText(selectedItem.getProductId());
        productToAdd.setText(selectedItem.getProductId());
    }
}
