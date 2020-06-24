package GUIControllers.SellerAccount;

import Controller.Control;
import Controller.ControlSeller;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Seller;
import Model.Product;
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

public class Products extends GraphicFather implements Initializable {
    public TextField productToRemove;
    public TextField productToEdit;
    public TextField productToBuyers;
    public TableView<Product> listProducts;
    public TextField productToView;
    public Label alertLabel;
    public TextField productToAdd;
    Seller seller = (Seller)Control.getInstance().getUser();

    ObservableList<Product> getProducts(){
        ObservableList<Product> result =  FXCollections.observableArrayList();
        result.addAll(seller.getAllProducts());
        return result;
    }

    public TableColumn<Product,String> name = new TableColumn<>("Name");
    public TableColumn<Product,String> id = new TableColumn<>("ProductId");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        try
        {
            String reqID = ControlSeller.getInstance().sendRemoveProductReq(productToRemove.getText());
            showError(alertLabel,"Your request with ID " + reqID + " has been sent!",Error.POSITIVE);
        }
        catch (Exception e)
        {
            showError(alertLabel,e.getMessage(),Error.NEGATIVE);
        }
    }

    public void editProduct(MouseEvent mouseEvent) {
        if(ControlSeller.getInstance().checkProductExists(productToEdit.getText())&&ControlSeller.getInstance().checkSellerGotProduct(productToEdit.getText(), seller)){
            ControlSeller.getInstance().setProductToEdit(productToEdit.getText());
            goToNextPage(Page.EDITPRODUCT,mouseEvent);
        }
        else{
            showError(alertLabel,"You don't have this product!",Error.NEGATIVE);
        }
    }

    public void viewBuyers(MouseEvent mouseEvent) {
        if(ControlSeller.getInstance().checkProductExists(productToBuyers.getText())&&ControlSeller.getInstance().checkSellerGotProduct(productToBuyers.getText(), seller)){
            ControlSeller.getInstance().setProductViewBuyers(productToBuyers.getText());
            goToNextPage(Page.VIEWBUYERSPRODUCT,mouseEvent);
        }
        else{
            showError(alertLabel,"You don't have this product!",Error.NEGATIVE);
        }
    }

    public void viewProduct(MouseEvent mouseEvent) {
//        if(ControlSeller.getInstance().checkProductExists(productToView.getText())&&ControlSeller.getInstance().checkSellerGotProduct(productToView.getText(), seller)){
//            ControlSeller.getInstance().setProductToView(productToView.getText());
//            goToNextPage(Page.VIEWPRODUCT,mouseEvent);
//        }
//        else{
//            showError(alertLabel,"You don't have this product!", Error.NEGATIVE);
//        }
    }

    public void addProductToList(MouseEvent mouseEvent) {
        try
        {
            String reqID = ControlSeller.getInstance().sendAddSellerProductReq(productToAdd.getText());
            showError(alertLabel,"Your request with id " + reqID + " has been sent!",Error.POSITIVE);
        }
        catch (Exception e)
        {
            showError(alertLabel,e.getMessage(),Error.NEGATIVE);
        }
    }
}
